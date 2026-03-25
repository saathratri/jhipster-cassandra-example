import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, HostListener, NgZone, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Data, ParamMap, Router, RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import dayjs from 'dayjs/esm';
import customParseFormat from 'dayjs/esm/plugin/customParseFormat';
import { Observable, Subscription, combineLatest, filter, finalize, map, tap } from 'rxjs';

import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';

dayjs.extend(customParseFormat);
import { MaterialModule } from 'app/shared/material.module';

// Saathratri: Composite Primary Key Code
import { SaathratriEntity2DeleteDialogComponent } from '../delete/saathratri-entity-2-delete-dialog';
import { ISaathratriEntity2, ISaathratriEntity2Id } from '../saathratri-entity-2.model';
import { EntityArrayResponseType, EntityResponseType, SaathratriEntity2Service } from '../service/saathratri-entity-2.service';

@Component({
  selector: 'jhi-saathratri-entity-2',
  templateUrl: './saathratri-entity-2.html',
  imports: [
    RouterLink,
    FormsModule,
    FontAwesomeModule,
    AlertError,
    Alert,
    TranslateDirective,
    TranslateModule,
    SortDirective,
    SortByDirective,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    ConvertFromDayjsToDateLongPipe,
    MaterialModule,
  ],
})
export class SaathratriEntity2Component implements OnInit {
  subscription: Subscription | null = null;
  readonly saathratriEntity2s = signal<ISaathratriEntity2[]>([]);

  sortState = sortStateSignal({});

  isLoadingMore = false;
  // Cassandra Slice pagination state (cursor-based)
  pagingState: string | null = null;
  pageSize = 20;
  hasNextPage = false;
  totalItems: number | null = null;

  // Cassandra search form state
  isSearchFormCollapsed = true;
  isSearchActive = false;
  searchCriteria: {
    entityTypeId: string | null;
    yearOfDateAdded: number | null;
    arrivalDateOperator: string | null;
    arrivalDateDate: Date | null;
    arrivalDate: number | null;
    blogIdOperator: string | null;
    blogId: string | null;
  } = {
    entityTypeId: null,
    yearOfDateAdded: null,
    arrivalDateOperator: 'eq',
    arrivalDateDate: null,
    arrivalDate: null,
    blogIdOperator: 'eq',
    blogId: null,
  };

  public readonly router = inject(Router);
  protected readonly saathratriEntity2Service = inject(SaathratriEntity2Service);
  // Cassandra entities use Observable-based loading
  readonly isLoading = signal(false);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  constructor() {}

  // Saathratri: Composite Primary Key Code
  trackCompositeId = (item: ISaathratriEntity2): ISaathratriEntity2Id => this.saathratriEntity2Service.getSaathratriEntity2Identifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.saathratriEntity2s().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(saathratriEntity2: ISaathratriEntity2): void {
    const modalRef = this.modalService.open(SaathratriEntity2DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.saathratriEntity2 = saathratriEntity2;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.resetAndLoad()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  resetAndLoad(): void {
    this.pagingState = null;
    this.hasNextPage = false;
    this.totalItems = null;
    this.saathratriEntity2s.set([]);
    this.load();
  }

  loadMore(): void {
    if (this.hasNextPage && !this.isLoadingMore) {
      this.isLoadingMore = true;
      this.queryBackend().subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res, true);
          this.isLoadingMore = false;
        },
        error: () => {
          this.isLoadingMore = false;
        },
      });
    }
  }

  @HostListener('window:scroll')
  onWindowScroll(): void {
    const scrollPosition = window.scrollY + window.innerHeight;
    const pageHeight = document.documentElement.scrollHeight;
    const threshold = 200;

    if (scrollPosition >= pageHeight - threshold && this.hasNextPage && !this.isLoadingMore && !this.isLoading()) {
      this.loadMore();
    }
  }

  // Cassandra search form methods
  toggleSearchForm(): void {
    this.isSearchFormCollapsed = !this.isSearchFormCollapsed;
  }

  hasActiveSearch(): boolean {
    return this.isSearchActive;
  }

  isSearchFormValid(): boolean {
    if (this.searchCriteria.entityTypeId === null || this.searchCriteria.entityTypeId === undefined) {
      return false;
    }
    if (this.searchCriteria.yearOfDateAdded === null || this.searchCriteria.yearOfDateAdded === undefined) {
      return false;
    }
    return true;
  }

  isClusteringFieldDisabled(fieldName: string): boolean {
    const criteria = this.searchCriteria as any;
    const allClusteringFieldNames: string[] = ['arrivalDate', 'blogId'];
    const fieldsWithOperators: string[] = ['arrivalDate', 'blogId'];

    const fieldIndex = allClusteringFieldNames.indexOf(fieldName);
    if (fieldIndex <= 0) {
      return false;
    }

    for (let i = 0; i < fieldIndex; i++) {
      const prevFieldName = allClusteringFieldNames[i];
      if (fieldsWithOperators.includes(prevFieldName)) {
        const prevOperator = criteria[`${prevFieldName}Operator`];
        if (prevOperator && prevOperator !== 'eq') {
          return true;
        }
      }
    }
    return false;
  }

  onOperatorChange(fieldName: string): void {
    const criteria = this.searchCriteria as any;
    const allClusteringFieldNames: string[] = ['arrivalDate', 'blogId'];
    const fieldsWithOperators: string[] = ['arrivalDate', 'blogId'];

    const fieldIndex = allClusteringFieldNames.indexOf(fieldName);
    if (fieldIndex < 0) return;

    const operator = criteria[`${fieldName}Operator`];
    if (operator && operator !== 'eq') {
      for (let i = fieldIndex + 1; i < allClusteringFieldNames.length; i++) {
        const nextFieldName = allClusteringFieldNames[i];
        criteria[nextFieldName] = null;
        if (fieldsWithOperators.includes(nextFieldName)) {
          criteria[`${nextFieldName}Operator`] = 'eq';
        }
        if (criteria[`${nextFieldName}Date`] !== undefined) {
          criteria[`${nextFieldName}Date`] = null;
        }
        if (criteria[`${nextFieldName}Hour`] !== undefined) {
          criteria[`${nextFieldName}Hour`] = null;
          criteria[`${nextFieldName}Minute`] = null;
          criteria[`${nextFieldName}AmPm`] = null;
        }
      }
    }
  }

  performSearch(): void {
    if (!this.isSearchFormValid()) {
      return;
    }
    this.isSearchActive = true;
    this.pagingState = null;
    this.hasNextPage = false;
    this.totalItems = null;
    this.saathratriEntity2s.set([]);
    this.load();
  }

  clearSearch(): void {
    this.searchCriteria.entityTypeId = null;
    this.searchCriteria.yearOfDateAdded = null;
    this.searchCriteria.arrivalDateOperator = 'eq';
    this.searchCriteria.arrivalDateDate = null;
    this.searchCriteria.arrivalDate = null;
    this.searchCriteria.blogIdOperator = 'eq';
    this.searchCriteria.blogId = null;
    this.isSearchActive = false;
    this.pagingState = null;
    this.hasNextPage = false;
    this.totalItems = null;
    this.saathratriEntity2s.set([]);
    this.load();
  }

  onSearchDateChange(fieldName: string, isDateTime: boolean = false): void {
    const criteria = this.searchCriteria as any;
    const dateValue: Date | null = criteria[`${fieldName}Date`];

    if (!dateValue) {
      criteria[fieldName] = null;
      return;
    }

    if (isDateTime) {
      const hour: number | null = criteria[`${fieldName}Hour`];
      const minute: number | null = criteria[`${fieldName}Minute`];
      const amPm: string | null = criteria[`${fieldName}AmPm`];

      if (hour === null || minute === null || !amPm) {
        const combinedDateTime = new Date(dateValue);
        combinedDateTime.setHours(0, 0, 0, 0);
        criteria[fieldName] = combinedDateTime.getTime();
        return;
      }

      let adjustedHours = hour;
      if (amPm === 'PM' && hour !== 12) {
        adjustedHours = hour + 12;
      } else if (amPm === 'AM' && hour === 12) {
        adjustedHours = 0;
      }

      const combinedDateTime = new Date(dateValue);
      combinedDateTime.setHours(adjustedHours, minute, 0, 0);
      criteria[fieldName] = combinedDateTime.getTime();
    } else {
      const dateOnly = new Date(dateValue);
      dateOnly.setHours(0, 0, 0, 0);
      criteria[fieldName] = dateOnly.getTime();
    }
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(event);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType, append = false): void {
    this.extractPaginationHeaders(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);

    if (append && dataFromBody.length === 0) {
      this.hasNextPage = false;
      return;
    }

    if (append) {
      const current = this.saathratriEntity2s();
      const existingKeys = new Set(current.map(item => this.getEntityKey(item)));
      const newItems = dataFromBody.filter(item => !existingKeys.has(this.getEntityKey(item)));
      if (newItems.length === 0) {
        this.hasNextPage = false;
        return;
      }
      this.saathratriEntity2s.set(this.refineData([...current, ...newItems]));
    } else {
      this.saathratriEntity2s.set(this.refineData(dataFromBody));
    }
    if (this.hasNextPage && this.totalItems !== null) {
      this.hasNextPage = this.saathratriEntity2s().length < this.totalItems;
    }
  }

  protected refineData(data: ISaathratriEntity2[]): ISaathratriEntity2[] {
    const { predicate, order } = this.sortState();
    if (!predicate || !order) {
      return data;
    }

    const compositeKeyFields = ['entityTypeId', 'yearOfDateAdded', 'arrivalDate', 'blogId'];

    return data.sort((a, b) => {
      let aVal: any;
      let bVal: any;

      if (compositeKeyFields.includes(predicate)) {
        aVal = a.compositeId[predicate as keyof ISaathratriEntity2Id];
        bVal = b.compositeId[predicate as keyof ISaathratriEntity2Id];
      } else {
        aVal = (a as any)[predicate];
        bVal = (b as any)[predicate];
      }

      if (aVal == null && bVal == null) return 0;
      if (aVal == null) return 1;
      if (bVal == null) return -1;

      let result = 0;
      if (typeof aVal === 'string' && typeof bVal === 'string') {
        result = aVal.localeCompare(bVal);
      } else if (aVal < bVal) {
        result = -1;
      } else if (aVal > bVal) {
        result = 1;
      }

      return order === 'asc' ? result : -result;
    });
  }

  protected fillComponentAttributesFromResponseBody(data: ISaathratriEntity2[] | null): ISaathratriEntity2[] {
    return data ?? [];
  }

  protected extractPaginationHeaders(headers: HttpHeaders): void {
    const hasNextPage = headers.get('X-Has-Next-Page');
    this.hasNextPage = hasNextPage === 'true';

    const pagingStateHeader = headers.get('X-Paging-State');
    this.pagingState = pagingStateHeader || null;

    const totalCountHeader = headers.get('X-Total-Count');
    this.totalItems = totalCountHeader !== null ? Number(totalCountHeader) : null;
  }

  private getEntityKey(item: ISaathratriEntity2): string {
    return this.getCompositeKey(item);
  }

  private getCompositeKey(item: ISaathratriEntity2): string {
    const compositeId = item.compositeId;
    return [compositeId.entityTypeId, compositeId.yearOfDateAdded, compositeId.arrivalDate, compositeId.blogId].join('|');
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading.set(true);
    const queryObject: any = {
      pagingState: this.pagingState,
      size: this.pageSize,
      sort: this.sortService.buildSortParam(this.sortState()),
    };

    if (this.isSearchActive) {
      if (
        this.searchCriteria.entityTypeId &&
        this.searchCriteria.entityTypeId.trim() !== '' &&
        this.searchCriteria.yearOfDateAdded !== null &&
        this.searchCriteria.yearOfDateAdded !== undefined &&
        this.searchCriteria.arrivalDate !== null &&
        this.searchCriteria.arrivalDate !== undefined &&
        this.searchCriteria.blogId &&
        this.searchCriteria.blogId.trim() !== ''
      ) {
        const operatorBlogId = this.searchCriteria.blogIdOperator || 'eq';
        if (operatorBlogId === 'eq') {
          return this.saathratriEntity2Service
            .findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
              this.searchCriteria.entityTypeId!,
              this.searchCriteria.yearOfDateAdded!,
              this.searchCriteria.arrivalDate!,
              this.searchCriteria.blogId!,
            )
            .pipe(
              map((res: EntityResponseType) => {
                const entity = res.body;
                return new HttpResponse({
                  body: entity ? [entity] : [],
                  headers: res.headers,
                  status: res.status,
                });
              }),
              finalize(() => this.isLoading.set(false)),
            );
        } else if (operatorBlogId === 'lt') {
          return this.saathratriEntity2Service
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanPageable(
              this.searchCriteria.entityTypeId!,
              this.searchCriteria.yearOfDateAdded!,
              this.searchCriteria.arrivalDate!,
              this.searchCriteria.blogId!,
              queryObject,
            )
            .pipe(finalize(() => this.isLoading.set(false)));
        } else if (operatorBlogId === 'lte') {
          return this.saathratriEntity2Service
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdLessThanEqualPageable(
              this.searchCriteria.entityTypeId!,
              this.searchCriteria.yearOfDateAdded!,
              this.searchCriteria.arrivalDate!,
              this.searchCriteria.blogId!,
              queryObject,
            )
            .pipe(finalize(() => this.isLoading.set(false)));
        } else if (operatorBlogId === 'gt') {
          return this.saathratriEntity2Service
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanPageable(
              this.searchCriteria.entityTypeId!,
              this.searchCriteria.yearOfDateAdded!,
              this.searchCriteria.arrivalDate!,
              this.searchCriteria.blogId!,
              queryObject,
            )
            .pipe(finalize(() => this.isLoading.set(false)));
        } else if (operatorBlogId === 'gte') {
          return this.saathratriEntity2Service
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogIdGreaterThanEqualPageable(
              this.searchCriteria.entityTypeId!,
              this.searchCriteria.yearOfDateAdded!,
              this.searchCriteria.arrivalDate!,
              this.searchCriteria.blogId!,
              queryObject,
            )
            .pipe(finalize(() => this.isLoading.set(false)));
        }
        return this.saathratriEntity2Service
          .findByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateAndCompositeIdBlogId(
            this.searchCriteria.entityTypeId!,
            this.searchCriteria.yearOfDateAdded!,
            this.searchCriteria.arrivalDate!,
            this.searchCriteria.blogId!,
          )
          .pipe(
            map((res: EntityResponseType) => {
              const entity = res.body;
              return new HttpResponse({
                body: entity ? [entity] : [],
                headers: res.headers,
                status: res.status,
              });
            }),
            finalize(() => this.isLoading.set(false)),
          );
      } else if (
        this.searchCriteria.entityTypeId &&
        this.searchCriteria.entityTypeId.trim() !== '' &&
        this.searchCriteria.yearOfDateAdded !== null &&
        this.searchCriteria.yearOfDateAdded !== undefined &&
        this.searchCriteria.arrivalDate !== null &&
        this.searchCriteria.arrivalDate !== undefined
      ) {
        const operatorArrivalDate = this.searchCriteria.arrivalDateOperator || 'eq';
        if (operatorArrivalDate === 'lt') {
          return this.saathratriEntity2Service
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanPageable(
              this.searchCriteria.entityTypeId!,
              this.searchCriteria.yearOfDateAdded!,
              this.searchCriteria.arrivalDate!,
              queryObject,
            )
            .pipe(finalize(() => this.isLoading.set(false)));
        } else if (operatorArrivalDate === 'lte') {
          return this.saathratriEntity2Service
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateLessThanEqualPageable(
              this.searchCriteria.entityTypeId!,
              this.searchCriteria.yearOfDateAdded!,
              this.searchCriteria.arrivalDate!,
              queryObject,
            )
            .pipe(finalize(() => this.isLoading.set(false)));
        } else if (operatorArrivalDate === 'gt') {
          return this.saathratriEntity2Service
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanPageable(
              this.searchCriteria.entityTypeId!,
              this.searchCriteria.yearOfDateAdded!,
              this.searchCriteria.arrivalDate!,
              queryObject,
            )
            .pipe(finalize(() => this.isLoading.set(false)));
        } else if (operatorArrivalDate === 'gte') {
          return this.saathratriEntity2Service
            .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDateGreaterThanEqualPageable(
              this.searchCriteria.entityTypeId!,
              this.searchCriteria.yearOfDateAdded!,
              this.searchCriteria.arrivalDate!,
              queryObject,
            )
            .pipe(finalize(() => this.isLoading.set(false)));
        }
        return this.saathratriEntity2Service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedAndCompositeIdArrivalDatePageable(
            this.searchCriteria.entityTypeId!,
            this.searchCriteria.yearOfDateAdded!,
            this.searchCriteria.arrivalDate!,
            queryObject,
          )
          .pipe(finalize(() => this.isLoading.set(false)));
      } else if (
        this.searchCriteria.entityTypeId &&
        this.searchCriteria.entityTypeId.trim() !== '' &&
        this.searchCriteria.yearOfDateAdded !== null &&
        this.searchCriteria.yearOfDateAdded !== undefined
      ) {
        return this.saathratriEntity2Service
          .findAllByCompositeIdEntityTypeIdAndCompositeIdYearOfDateAddedPageable(
            this.searchCriteria.entityTypeId!,
            this.searchCriteria.yearOfDateAdded!,
            queryObject,
          )
          .pipe(finalize(() => this.isLoading.set(false)));
      } else if (this.searchCriteria.entityTypeId && this.searchCriteria.entityTypeId.trim() !== '') {
        return this.saathratriEntity2Service
          .findAllByCompositeIdEntityTypeIdPageable(this.searchCriteria.entityTypeId!, queryObject)
          .pipe(finalize(() => this.isLoading.set(false)));
      }
    }
    // Fallback: no valid criteria
    return this.saathratriEntity2Service.querySlice(queryObject).pipe(finalize(() => this.isLoading.set(false)));
  }

  protected handleNavigation(sortState: SortState): void {
    this.pagingState = null;
    this.hasNextPage = false;
    this.totalItems = null;
    this.saathratriEntity2s.set([]);

    const queryParamsObj = {
      sort: this.sortService.buildSortParam(sortState),
    };

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }
}
