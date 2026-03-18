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
import { SetEntityByOrganizationDeleteDialogComponent } from '../delete/set-entity-by-organization-delete-dialog';
import { SetEntityByOrganizationService } from '../service/set-entity-by-organization.service';
import { ISetEntityByOrganization } from '../set-entity-by-organization.model';

@Component({
  selector: 'jhi-set-entity-by-organization',
  templateUrl: './set-entity-by-organization.html',
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
export class SetEntityByOrganizationComponent implements OnInit {
  subscription: Subscription | null = null;
  readonly setEntityByOrganizations = signal<ISetEntityByOrganization[]>([]);

  sortState = sortStateSignal({});

  public readonly router = inject(Router);
  protected readonly setEntityByOrganizationService = inject(SetEntityByOrganizationService);
  // eslint-disable-next-line @typescript-eslint/member-ordering
  readonly isLoading = this.setEntityByOrganizationService.setEntityByOrganizationsResource.isLoading;
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  constructor() {
    effect(() => {
      this.setEntityByOrganizations.set(
        this.fillComponentAttributesFromResponseBody([...this.setEntityByOrganizationService.setEntityByOrganizations()]),
      );
    });
  }

  // Saathratri: Single-value Primary Key Code
  trackOrganizationId = (item: ISetEntityByOrganization): string =>
    this.setEntityByOrganizationService.getSetEntityByOrganizationIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => {
          if (this.setEntityByOrganizations().length === 0) {
            this.load();
          }
        }),
      )
      .subscribe();
  }

  delete(setEntityByOrganization: ISetEntityByOrganization): void {
    const modalRef = this.modalService.open(SetEntityByOrganizationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.setEntityByOrganization = setEntityByOrganization;
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

  protected refineData(data: ISetEntityByOrganization[]): ISetEntityByOrganization[] {
    const { predicate, order } = this.sortState();
    return predicate && order ? data.sort(this.sortService.startSort({ predicate, order })) : data;
  }

  protected fillComponentAttributesFromResponseBody(data: ISetEntityByOrganization[]): ISetEntityByOrganization[] {
    return this.refineData(data);
  }

  protected queryBackend(): void {
    const queryObject: any = {
      sort: this.sortService.buildSortParam(this.sortState()),
    };
    this.setEntityByOrganizationService.setEntityByOrganizationsParams.set(queryObject);
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
