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
// Saathratri: Single-value Primary Key Code
import { LandingPageByOrganizationDeleteDialogComponent } from '../delete/landing-page-by-organization-delete-dialog';
import { ILandingPageByOrganization } from '../landing-page-by-organization.model';
import { LandingPageByOrganizationService } from '../service/landing-page-by-organization.service';

@Component({
  selector: 'jhi-landing-page-by-organization',
  templateUrl: './landing-page-by-organization.html',
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
export class LandingPageByOrganizationComponent implements OnInit {
  subscription: Subscription | null = null;
  readonly landingPageByOrganizations = signal<ILandingPageByOrganization[]>([]);

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly landingPageByOrganizationService = inject(LandingPageByOrganizationService);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  readonly isLoading = this.landingPageByOrganizationService.landingPageByOrganizationsResource.isLoading;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  constructor() {
    effect(() => {
      this.landingPageByOrganizations.set(
        this.fillComponentAttributesFromResponseBody([...this.landingPageByOrganizationService.landingPageByOrganizations()]),
      );
    });
  }

  // Saathratri: Single-value Primary Key Code
  trackOrganizationId = (item: ILandingPageByOrganization): string =>
    this.landingPageByOrganizationService.getLandingPageByOrganizationIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.landingPageByOrganizations().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(landingPageByOrganization: ILandingPageByOrganization): void {
    const modalRef = this.modalService.open(LandingPageByOrganizationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.landingPageByOrganization = landingPageByOrganization;
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

  protected refineData(data: ILandingPageByOrganization[]): ILandingPageByOrganization[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: ILandingPageByOrganization[]): ILandingPageByOrganization[] {
    return this.refineData(data);
  }

  protected queryBackend(): void {
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    this.landingPageByOrganizationService.landingPageByOrganizationsParams.set(queryObject);
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
