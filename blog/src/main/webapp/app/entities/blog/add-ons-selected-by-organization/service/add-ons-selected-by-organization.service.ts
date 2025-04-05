import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {
  IAddOnsSelectedByOrganization,
  IAddOnsSelectedByOrganizationId,
  NewAddOnsSelectedByOrganization,
} from '../add-ons-selected-by-organization.model';
export type PartialUpdateAddOnsSelectedByOrganization = Partial<IAddOnsSelectedByOrganization> &
  Pick<IAddOnsSelectedByOrganization, 'compositeId'>;

type RestOf<T extends IAddOnsSelectedByOrganization | NewAddOnsSelectedByOrganization> = Omit<
  T,
  'arrivalDate' | 'departureDate' | 'addOnDetailsBigInt'
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

@Injectable({ providedIn: 'root' })
export class AddOnsSelectedByOrganizationService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/add-ons-selected-by-organizations', 'blog');

  create(addOnsSelectedByOrganization: NewAddOnsSelectedByOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(addOnsSelectedByOrganization);
    return this.http
      .post<RestAddOnsSelectedByOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(addOnsSelectedByOrganization: IAddOnsSelectedByOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(addOnsSelectedByOrganization);
    return this.http
      .put<RestAddOnsSelectedByOrganization>(
        `${this.resourceUrl}/${addOnsSelectedByOrganization.compositeId.organizationId}/${copy.compositeId.arrivalDate}/${addOnsSelectedByOrganization.compositeId.accountNumber}/${addOnsSelectedByOrganization.compositeId.createdTimeId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(addOnsSelectedByOrganization: PartialUpdateAddOnsSelectedByOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(addOnsSelectedByOrganization);
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

  delete(addOnsSelectedByOrganization: IAddOnsSelectedByOrganization): Observable<HttpResponse<{}>> {
    const copy = this.convertDateFromClient(addOnsSelectedByOrganization);
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

  protected convertDateFromClient<
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

  protected convertDateFromServer(restAddOnsSelectedByOrganization: RestAddOnsSelectedByOrganization): IAddOnsSelectedByOrganization {
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

  protected convertResponseFromServer(res: HttpResponse<RestAddOnsSelectedByOrganization>): HttpResponse<IAddOnsSelectedByOrganization> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestAddOnsSelectedByOrganization[]>,
  ): HttpResponse<IAddOnsSelectedByOrganization[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
