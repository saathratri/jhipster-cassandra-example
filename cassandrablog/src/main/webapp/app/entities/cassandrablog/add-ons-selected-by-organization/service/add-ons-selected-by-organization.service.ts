import { HttpClient, HttpParams, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import {
  IAddOnsSelectedByOrganization,
  IAddOnsSelectedByOrganizationId,
  NewAddOnsSelectedByOrganization,
} from '../add-ons-selected-by-organization.model';
export type PartialUpdateAddOnsSelectedByOrganization = Partial<IAddOnsSelectedByOrganization> &
  Pick<IAddOnsSelectedByOrganization, 'compositeId'>;

type RestOf<T extends IAddOnsSelectedByOrganization | NewAddOnsSelectedByOrganization> = Omit<
  T,
  'arrivalDate' | 'createdTimeId' | 'departureDate' | 'addOnDetailsBigInt'
> & {
  compositeId: {
    arrivalDate?: number | null;
  };
  departureDate?: number | null;
  addOnDetailsText?: Record<string, string> | null;
  addOnDetailsDecimal?: Record<string, number> | null;
  addOnDetailsBoolean?: Record<string, boolean> | null;
  addOnDetailsBigInt?: Record<string, dayjs.Dayjs> | null;
};

export type RestAddOnsSelectedByOrganization = RestOf<IAddOnsSelectedByOrganization>;

export type NewRestAddOnsSelectedByOrganization = RestOf<NewAddOnsSelectedByOrganization>;

export type PartialUpdateRestAddOnsSelectedByOrganization = RestOf<PartialUpdateAddOnsSelectedByOrganization>;

export type EntityResponseType = HttpResponse<IAddOnsSelectedByOrganization>;
export type EntityArrayResponseType = HttpResponse<IAddOnsSelectedByOrganization[]>;

@Injectable()
export class AddOnsSelectedByOrganizationsService {
  readonly addOnsSelectedByOrganizationsParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly addOnsSelectedByOrganizationsResource = httpResource<RestAddOnsSelectedByOrganization[]>(() => {
    const params = this.addOnsSelectedByOrganizationsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of addOnsSelectedByOrganization that have been fetched.
   */
  readonly addOnsSelectedByOrganizations = computed(() =>
    (this.addOnsSelectedByOrganizationsResource.hasValue() ? this.addOnsSelectedByOrganizationsResource.value() : []).map(item =>
      this.convertValueFromServer(item),
    ),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/add-ons-selected-by-organizations', 'cassandrablog');

  protected convertValueFromServer(restAddOnsSelectedByOrganization: RestAddOnsSelectedByOrganization): IAddOnsSelectedByOrganization {
    return {
      ...restAddOnsSelectedByOrganization,
      compositeId: {
        organizationId: restAddOnsSelectedByOrganization.compositeId.organizationId,
        arrivalDate: restAddOnsSelectedByOrganization.compositeId.arrivalDate
          ? dayjs(restAddOnsSelectedByOrganization.compositeId.arrivalDate)
          : null,

        accountNumber: restAddOnsSelectedByOrganization.compositeId.accountNumber,

        createdTimeId: restAddOnsSelectedByOrganization.compositeId.createdTimeId,
      },
      departureDate: restAddOnsSelectedByOrganization.departureDate ? dayjs(restAddOnsSelectedByOrganization.departureDate) : null,
      addOnDetailsBigInt: restAddOnsSelectedByOrganization.addOnDetailsBigInt
        ? // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
          Object.fromEntries(Object.entries(restAddOnsSelectedByOrganization.addOnDetailsBigInt).map(([k, v]) => [k, dayjs(v)]))
        : {},
    };
  }
}

@Injectable({ providedIn: 'root' })
export class AddOnsSelectedByOrganizationService extends AddOnsSelectedByOrganizationsService {
  protected readonly http = inject(HttpClient);

  create(addOnsSelectedByOrganization: NewAddOnsSelectedByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(addOnsSelectedByOrganization);
    return this.http
      .post<RestAddOnsSelectedByOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(addOnsSelectedByOrganization: IAddOnsSelectedByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(addOnsSelectedByOrganization);
    return this.http
      .put<RestAddOnsSelectedByOrganization>(
        `${this.resourceUrl}/${addOnsSelectedByOrganization.compositeId.organizationId}/${copy.compositeId.arrivalDate}/${addOnsSelectedByOrganization.compositeId.accountNumber}/${addOnsSelectedByOrganization.compositeId.createdTimeId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(addOnsSelectedByOrganization: PartialUpdateAddOnsSelectedByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(addOnsSelectedByOrganization);
    return this.http
      .patch<RestAddOnsSelectedByOrganization>(
        `${this.resourceUrl}/${addOnsSelectedByOrganization.compositeId.organizationId}/${copy.compositeId.arrivalDate}/${addOnsSelectedByOrganization.compositeId.accountNumber}/${addOnsSelectedByOrganization.compositeId.createdTimeId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(organizationId: string, arrivalDate: number, accountNumber: string, createdTimeId: string): Observable<EntityResponseType> {
    const data = { organizationId, arrivalDate, accountNumber, createdTimeId };
    return this.http
      .get<RestAddOnsSelectedByOrganization>(`${this.resourceUrl}/get`, { params: data, observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAddOnsSelectedByOrganization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAddOnsSelectedByOrganization[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdPageable(organizationId: string, req?: any): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', String(organizationId));
    return this.http
      .get<
        RestAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable(
    organizationId: string,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', String(organizationId));
    options = options.set('arrivalDate', String(arrivalDate));
    return this.http
      .get<
        RestAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', String(organizationId));
    options = options.set('arrivalDate', String(arrivalDate));
    options = options.set('accountNumber', String(accountNumber));
    return this.http
      .get<
        RestAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdPageable(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', String(organizationId));
    options = options.set('arrivalDate', String(arrivalDate));
    options = options.set('accountNumber', String(accountNumber));
    options = options.set('createdTimeId', String(createdTimeId));
    return this.http
      .get<
        RestAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable(
    organizationId: string,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', String(organizationId));
    options = options.set('arrivalDate', String(arrivalDate));
    return this.http
      .get<
        RestAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable(
    organizationId: string,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', String(organizationId));
    options = options.set('arrivalDate', String(arrivalDate));
    return this.http
      .get<
        RestAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-less-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable(
    organizationId: string,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', String(organizationId));
    options = options.set('arrivalDate', String(arrivalDate));
    return this.http
      .get<
        RestAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable(
    organizationId: string,
    arrivalDate: number,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', String(organizationId));
    options = options.set('arrivalDate', String(arrivalDate));
    return this.http
      .get<
        RestAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-greater-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', String(organizationId));
    options = options.set('arrivalDate', String(arrivalDate));
    options = options.set('accountNumber', String(accountNumber));
    options = options.set('createdTimeId', String(createdTimeId));
    return this.http
      .get<
        RestAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', String(organizationId));
    options = options.set('arrivalDate', String(arrivalDate));
    options = options.set('accountNumber', String(accountNumber));
    options = options.set('createdTimeId', String(createdTimeId));
    return this.http
      .get<
        RestAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-less-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', String(organizationId));
    options = options.set('arrivalDate', String(arrivalDate));
    options = options.set('accountNumber', String(accountNumber));
    options = options.set('createdTimeId', String(createdTimeId));
    return this.http
      .get<
        RestAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    createdTimeId: string,
    req?: any,
  ): Observable<EntityArrayResponseType> {
    let options = createRequestOption(req);
    options = options.set('organizationId', String(organizationId));
    options = options.set('arrivalDate', String(arrivalDate));
    options = options.set('accountNumber', String(accountNumber));
    options = options.set('createdTimeId', String(createdTimeId));
    return this.http
      .get<
        RestAddOnsSelectedByOrganization[]
      >(`${this.resourceUrl}/find-all-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id-greater-than-equal-pageable`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
    organizationId: string,
    arrivalDate: number,
    accountNumber: string,
    createdTimeId: string,
  ): Observable<EntityResponseType> {
    let options = new HttpParams();
    options = options.set('organizationId', String(organizationId));
    options = options.set('arrivalDate', String(arrivalDate));
    options = options.set('accountNumber', String(accountNumber));
    options = options.set('createdTimeId', String(createdTimeId));
    return this.http
      .get<RestAddOnsSelectedByOrganization>(
        `${this.resourceUrl}/find-by-composite-id-organization-id-and-composite-id-arrival-date-and-composite-id-account-number-and-composite-id-created-time-id`,
        { params: options, observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  delete(addOnsSelectedByOrganization: IAddOnsSelectedByOrganization): Observable<HttpResponse<{}>> {
    const copy = this.convertValueFromClient(addOnsSelectedByOrganization);
    return this.http.delete(
      `${this.resourceUrl}/${addOnsSelectedByOrganization.compositeId.organizationId}/${copy.compositeId.arrivalDate}/${addOnsSelectedByOrganization.compositeId.accountNumber}/${addOnsSelectedByOrganization.compositeId.createdTimeId}`,
      { observe: 'response' },
    );
  }

  getAddOnsSelectedByOrganizationIdentifier(
    addOnsSelectedByOrganization: Pick<IAddOnsSelectedByOrganization, 'compositeId'>,
  ): IAddOnsSelectedByOrganizationId {
    return addOnsSelectedByOrganization.compositeId;
  }
  compareAddOnsSelectedByOrganization(
    o1: Pick<IAddOnsSelectedByOrganization, 'compositeId'> | null,
    o2: Pick<IAddOnsSelectedByOrganization, 'compositeId'> | null,
  ): boolean {
    return o1 && o2 ? this.getAddOnsSelectedByOrganizationIdentifier(o1) === this.getAddOnsSelectedByOrganizationIdentifier(o2) : o1 === o2;
  }
  addAddOnsSelectedByOrganizationToCollectionIfMissing<Type extends Pick<IAddOnsSelectedByOrganization, 'compositeId'>>(
    addOnsSelectedByOrganizationCollection: Type[],
    ...addOnsSelectedByOrganizationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const addOnsSelectedByOrganizations: Type[] = addOnsSelectedByOrganizationsToCheck.filter(isPresent);
    if (addOnsSelectedByOrganizations.length > 0) {
      const addOnsSelectedByOrganizationCollectionIdentifiers = addOnsSelectedByOrganizationCollection.map(
        addOnsSelectedByOrganizationItem => this.getAddOnsSelectedByOrganizationIdentifier(addOnsSelectedByOrganizationItem),
      );
      const addOnsSelectedByOrganizationsToAdd = addOnsSelectedByOrganizations.filter(addOnsSelectedByOrganizationItem => {
        const addOnsSelectedByOrganizationIdentifier = this.getAddOnsSelectedByOrganizationIdentifier(addOnsSelectedByOrganizationItem);
        if (addOnsSelectedByOrganizationCollectionIdentifiers.includes(addOnsSelectedByOrganizationIdentifier)) {
          return false;
        }
        addOnsSelectedByOrganizationCollectionIdentifiers.push(addOnsSelectedByOrganizationIdentifier);
        return true;
      });
      return [...addOnsSelectedByOrganizationsToAdd, ...addOnsSelectedByOrganizationCollection];
    }
    return addOnsSelectedByOrganizationCollection;
  }

  protected convertValueFromClient<
    T extends IAddOnsSelectedByOrganization | NewAddOnsSelectedByOrganization | PartialUpdateAddOnsSelectedByOrganization,
  >(addOnsSelectedByOrganization: T): RestOf<T> {
    return {
      ...addOnsSelectedByOrganization,
      compositeId: {
        ...addOnsSelectedByOrganization.compositeId,

        arrivalDate: addOnsSelectedByOrganization.compositeId.arrivalDate
          ? addOnsSelectedByOrganization.compositeId.arrivalDate.valueOf()
          : null,
      },
      departureDate: addOnsSelectedByOrganization.departureDate ? addOnsSelectedByOrganization.departureDate.valueOf() : null,
      /* eslint-disable @typescript-eslint/no-unnecessary-condition */
      addOnDetailsBigInt: addOnsSelectedByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(Object.entries(addOnsSelectedByOrganization.addOnDetailsBigInt).map(([k, v]) => [k, v?.valueOf()]))
        : {},
      /* eslint-enable @typescript-eslint/no-unnecessary-condition */
    } as RestOf<T>;
  }

  protected convertResponseFromServer(res: HttpResponse<RestAddOnsSelectedByOrganization>): HttpResponse<IAddOnsSelectedByOrganization> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestAddOnsSelectedByOrganization[]>,
  ): HttpResponse<IAddOnsSelectedByOrganization[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
