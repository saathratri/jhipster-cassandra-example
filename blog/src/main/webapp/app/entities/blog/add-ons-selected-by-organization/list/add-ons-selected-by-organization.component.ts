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
import { IAddOnsSelectedByOrganization, IAddOnsSelectedByOrganizationId } from '../add-ons-selected-by-organization.model';
import { AddOnsSelectedByOrganizationService, EntityArrayResponseType } from '../service/add-ons-selected-by-organization.service';
import { AddOnsSelectedByOrganizationDeleteDialogComponent } from '../delete/add-ons-selected-by-organization-delete-dialog.component';

@Component({
  standalone: true,
  selector: 'jhi-add-ons-selected-by-organization',
  templateUrl: './add-ons-selected-by-organization.component.html',
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
export class AddOnsSelectedByOrganizationComponent implements OnInit {
  subscription: Subscription | null = null;
  addOnsSelectedByOrganizations?: IAddOnsSelectedByOrganization[];
  isLoading = false;

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly addOnsSelectedByOrganizationService = inject(AddOnsSelectedByOrganizationService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);
  // Saathratri: Composite Primary Key Code
  trackCompositeId = (_index: number, item: IAddOnsSelectedByOrganizationId): IAddOnsSelectedByOrganizationId =>
    this.addOnsSelectedByOrganizationService.getAddOnsSelectedByOrganizationIdentifier({ compositeId: item });

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (!this.addOnsSelectedByOrganizations || this.addOnsSelectedByOrganizations.length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(addOnsSelectedByOrganization: IAddOnsSelectedByOrganization): void {
    const modalRef = this.modalService.open(AddOnsSelectedByOrganizationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.addOnsSelectedByOrganization = addOnsSelectedByOrganization;
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
    this.addOnsSelectedByOrganizations = this.refineData(dataFromBody);
  }

  protected refineData(data: IAddOnsSelectedByOrganization[]): IAddOnsSelectedByOrganization[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IAddOnsSelectedByOrganization[] | null): IAddOnsSelectedByOrganization[] {
    return data ?? [];
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    return this.addOnsSelectedByOrganizationService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
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
