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
// Saathratri: Single-value Primary Key Code
import { SaathratriEntityDeleteDialogComponent } from '../delete/saathratri-entity-delete-dialog';
import { ISaathratriEntity } from '../saathratri-entity.model';
import { SaathratriEntityService } from '../service/saathratri-entity.service';

@Component({
  selector: 'jhi-saathratri-entity',
  templateUrl: './saathratri-entity.html',
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
export class SaathratriEntityComponent implements OnInit {
  subscription: Subscription | null = null;
  readonly saathratriEntities = signal<ISaathratriEntity[]>([]);

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly saathratriEntityService = inject(SaathratriEntityService);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  readonly isLoading = this.saathratriEntityService.saathratriEntitiesResource.isLoading;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  constructor() {
    effect(() => {
      this.saathratriEntities.set(this.fillComponentAttributesFromResponseBody([...this.saathratriEntityService.saathratriEntities()]));
    });
  }

  // Saathratri: Single-value Primary Key Code
  trackEntityId = (item: ISaathratriEntity): string => this.saathratriEntityService.getSaathratriEntityIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.saathratriEntities().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(saathratriEntity: ISaathratriEntity): void {
    const modalRef = this.modalService.open(SaathratriEntityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.saathratriEntity = saathratriEntity;
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

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected refineData(data: ISaathratriEntity[]): ISaathratriEntity[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: ISaathratriEntity[]): ISaathratriEntity[] {
    return this.refineData(data);
  }

  protected queryBackend(): void {
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    this.saathratriEntityService.saathratriEntitiesParams.set(queryObject);
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
