import { Component, NgZone, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { FormsModule } from '@angular/forms';
// Saathratri: Composite Primary Key Code
import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { ISaathratriEntity4, ISaathratriEntity4Id } from '../saathratri-entity-4.model';
import { EntityArrayResponseType, SaathratriEntity4Service } from '../service/saathratri-entity-4.service';
import { SaathratriEntity4DeleteDialogComponent } from '../delete/saathratri-entity-4-delete-dialog.component';

@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-4',
  templateUrl: './saathratri-entity-4.component.html',
  imports: [
    RouterModule,
    FormsModule,
    SharedModule,
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
  saathratriEntity4s?: ISaathratriEntity4[];
  isLoading = false;

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly saathratriEntity4Service = inject(SaathratriEntity4Service);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);
  // Saathratri: Composite Primary Key Code
  trackCompositeId = (_index: number, item: ISaathratriEntity4Id): ISaathratriEntity4Id =>
    this.saathratriEntity4Service.getSaathratriEntity4Identifier({ compositeId: item });

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (!this.saathratriEntity4s || this.saathratriEntity4s.length === 0) {
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
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(event);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.saathratriEntity4s = this.refineData(dataFromBody);
  }

  protected refineData(data: ISaathratriEntity4[]): ISaathratriEntity4[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: ISaathratriEntity4[] | null): ISaathratriEntity4[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.saathratriEntity4Service.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
