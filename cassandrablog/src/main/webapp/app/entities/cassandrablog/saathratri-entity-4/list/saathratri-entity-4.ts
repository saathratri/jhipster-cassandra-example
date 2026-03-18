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
import { SaathratriEntity4DeleteDialogComponent } from '../delete/saathratri-entity-4-delete-dialog';
import { ISaathratriEntity4, ISaathratriEntity4Id } from '../saathratri-entity-4.model';
import { SaathratriEntity4Service } from '../service/saathratri-entity-4.service';

@Component({
  selector: 'jhi-saathratri-entity-4',
  templateUrl: './saathratri-entity-4.html',
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
export class SaathratriEntity4Component implements OnInit {
  subscription: Subscription | null = null;
  readonly saathratriEntity4s = signal<ISaathratriEntity4[]>([]);

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly saathratriEntity4Service = inject(SaathratriEntity4Service);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  readonly isLoading = this.saathratriEntity4Service.saathratriEntity4sResource.isLoading;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  constructor() {
    effect(() => {
      this.saathratriEntity4s.set(this.fillComponentAttributesFromResponseBody([...this.saathratriEntity4Service.saathratriEntity4s()]));
    });
  }

  // Saathratri: Composite Primary Key Code
  trackCompositeId = (item: ISaathratriEntity4): ISaathratriEntity4Id => this.saathratriEntity4Service.getSaathratriEntity4Identifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.saathratriEntity4s().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(saathratriEntity4: ISaathratriEntity4): void {
    const modalRef = this.modalService.open(SaathratriEntity4DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.saathratriEntity4 = saathratriEntity4;
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

  protected refineData(data: ISaathratriEntity4[]): ISaathratriEntity4[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: ISaathratriEntity4[]): ISaathratriEntity4[] {
    return this.refineData(data);
  }

  protected queryBackend(): void {
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    this.saathratriEntity4Service.saathratriEntity4sParams.set(queryObject);
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
