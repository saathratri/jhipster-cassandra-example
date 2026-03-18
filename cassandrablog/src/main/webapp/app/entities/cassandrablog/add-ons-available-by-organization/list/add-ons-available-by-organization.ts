import { KeyValuePipe } from '@angular/common';
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
import { IAddOnsAvailableByOrganization, IAddOnsAvailableByOrganizationId } from '../add-ons-available-by-organization.model';
import { AddOnsAvailableByOrganizationDeleteDialogComponent } from '../delete/add-ons-available-by-organization-delete-dialog';
import { AddOnsAvailableByOrganizationService } from '../service/add-ons-available-by-organization.service';

@Component({
  selector: 'jhi-add-ons-available-by-organization',
  templateUrl: './add-ons-available-by-organization.html',
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
    KeyValuePipe,
  ],
})
export class AddOnsAvailableByOrganizationComponent implements OnInit {
  subscription: Subscription | null = null;
  readonly addOnsAvailableByOrganizations = signal<IAddOnsAvailableByOrganization[]>([]);

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly addOnsAvailableByOrganizationService = inject(AddOnsAvailableByOrganizationService);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  readonly isLoading = this.addOnsAvailableByOrganizationService.addOnsAvailableByOrganizationsResource.isLoading;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  constructor() {
    effect(() => {
      this.addOnsAvailableByOrganizations.set(
        this.fillComponentAttributesFromResponseBody([...this.addOnsAvailableByOrganizationService.addOnsAvailableByOrganizations()]),
      );
    });
  }

  // Saathratri: Composite Primary Key Code
  trackCompositeId = (item: IAddOnsAvailableByOrganization): IAddOnsAvailableByOrganizationId =>
    this.addOnsAvailableByOrganizationService.getAddOnsAvailableByOrganizationIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.addOnsAvailableByOrganizations().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(addOnsAvailableByOrganization: IAddOnsAvailableByOrganization): void {
    const modalRef = this.modalService.open(AddOnsAvailableByOrganizationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.addOnsAvailableByOrganization = addOnsAvailableByOrganization;
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

  protected refineData(data: IAddOnsAvailableByOrganization[]): IAddOnsAvailableByOrganization[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IAddOnsAvailableByOrganization[]): IAddOnsAvailableByOrganization[] {
    return this.refineData(data);
  }

  protected queryBackend(): void {
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    this.addOnsAvailableByOrganizationService.addOnsAvailableByOrganizationsParams.set(queryObject);
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
