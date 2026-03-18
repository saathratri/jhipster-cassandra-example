import { Component, NgZone, OnInit, effect, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Data, ParamMap, Router, RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription, combineLatest, filter, tap } from 'rxjs';

import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
// Saathratri: Composite Primary Key Code
import { SaathratriEntity3DeleteDialogComponent } from '../delete/saathratri-entity-3-delete-dialog';
import { ISaathratriEntity3, ISaathratriEntity3Id } from '../saathratri-entity-3.model';
import { SaathratriEntity3Service } from '../service/saathratri-entity-3.service';

@Component({
  selector: 'jhi-saathratri-entity-3',
  templateUrl: './saathratri-entity-3.html',
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
  ],
})
export class SaathratriEntity3Component implements OnInit {
  subscription: Subscription | null = null;
  readonly saathratriEntity3s = signal<ISaathratriEntity3[]>([]);

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly saathratriEntity3Service = inject(SaathratriEntity3Service);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  readonly isLoading = this.saathratriEntity3Service.saathratriEntity3sResource.isLoading;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  constructor() {
    effect(() => {
      this.saathratriEntity3s.set(this.fillComponentAttributesFromResponseBody([...this.saathratriEntity3Service.saathratriEntity3s()]));
    });
  }

  // Saathratri: Composite Primary Key Code
  trackCompositeId = (item: ISaathratriEntity3): ISaathratriEntity3Id => this.saathratriEntity3Service.getSaathratriEntity3Identifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.saathratriEntity3s().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(saathratriEntity3: ISaathratriEntity3): void {
    const modalRef = this.modalService.open(SaathratriEntity3DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.saathratriEntity3 = saathratriEntity3;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend();
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(event);
  }

  toArray(set: Set<string> | null | undefined): string[] {
    return set ? Array.from(set) : [];
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected refineData(data: ISaathratriEntity3[]): ISaathratriEntity3[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: ISaathratriEntity3[]): ISaathratriEntity3[] {
    return this.refineData(data);
  }

  protected queryBackend(): void {
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    this.saathratriEntity3Service.saathratriEntity3sParams.set(queryObject);
  }

  protected handleNavigation(sortState: SortState): void {
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
