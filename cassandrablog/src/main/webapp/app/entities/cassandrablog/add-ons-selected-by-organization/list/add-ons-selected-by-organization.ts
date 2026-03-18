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
import { IAddOnsSelectedByOrganization, IAddOnsSelectedByOrganizationId } from '../add-ons-selected-by-organization.model';
import { AddOnsSelectedByOrganizationDeleteDialogComponent } from '../delete/add-ons-selected-by-organization-delete-dialog';
import { AddOnsSelectedByOrganizationService } from '../service/add-ons-selected-by-organization.service';

@Component({
  selector: 'jhi-add-ons-selected-by-organization',
  templateUrl: './add-ons-selected-by-organization.html',
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
export class AddOnsSelectedByOrganizationComponent implements OnInit {
  subscription: Subscription | null = null;
  readonly addOnsSelectedByOrganizations = signal<IAddOnsSelectedByOrganization[]>([]);

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly addOnsSelectedByOrganizationService = inject(AddOnsSelectedByOrganizationService);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  readonly isLoading = this.addOnsSelectedByOrganizationService.addOnsSelectedByOrganizationsResource.isLoading;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  constructor() {
    effect(() => {
      this.addOnsSelectedByOrganizations.set(
        this.fillComponentAttributesFromResponseBody([...this.addOnsSelectedByOrganizationService.addOnsSelectedByOrganizations()]),
      );
    });
  }

  // Saathratri: Composite Primary Key Code
  trackCompositeId = (item: IAddOnsSelectedByOrganization): IAddOnsSelectedByOrganizationId =>
    this.addOnsSelectedByOrganizationService.getAddOnsSelectedByOrganizationIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.addOnsSelectedByOrganizations().length === 0) {
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
    this.queryBackend();
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(event);
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));
  }

  protected refineData(data: IAddOnsSelectedByOrganization[]): IAddOnsSelectedByOrganization[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: IAddOnsSelectedByOrganization[]): IAddOnsSelectedByOrganization[] {
    return this.refineData(data);
  }

  protected queryBackend(): void {
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    this.addOnsSelectedByOrganizationService.addOnsSelectedByOrganizationsParams.set(queryObject);
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
