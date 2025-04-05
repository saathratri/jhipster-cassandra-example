import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {
  IAddOnsAvailableByOrganization,
  IAddOnsAvailableByOrganizationId,
  NewAddOnsAvailableByOrganization,
} from '../add-ons-available-by-organization.model';
export type PartialUpdateAddOnsAvailableByOrganization = Partial<IAddOnsAvailableByOrganization> &
  Pick<IAddOnsAvailableByOrganization, 'compositeId'>;

type RestOf<T extends IAddOnsAvailableByOrganization | NewAddOnsAvailableByOrganization> = Omit<T, 'addOnDetailsBigInt'> & {
  compositeId: {};
  addOnDetailsText?: Record<string, string> | null;
  addOnDetailsDecimal?: Record<string, number> | null;
  addOnDetailsBoolean?: Record<string, boolean> | null;
  addOnDetailsBigInt?: Record<string, dayjs.Dayjs> | null;
};

export type RestAddOnsAvailableByOrganization = RestOf<IAddOnsAvailableByOrganization>;

export type NewRestAddOnsAvailableByOrganization = RestOf<NewAddOnsAvailableByOrganization>;

export type PartialUpdateRestAddOnsAvailableByOrganization = RestOf<PartialUpdateAddOnsAvailableByOrganization>;

export type EntityResponseType = HttpResponse<IAddOnsAvailableByOrganization>;
export type EntityArrayResponseType = HttpResponse<IAddOnsAvailableByOrganization[]>;

@Injectable({ providedIn: 'root' })
export class AddOnsAvailableByOrganizationService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/add-ons-available-by-organizations', 'blog');

  create(addOnsAvailableByOrganization: NewAddOnsAvailableByOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(addOnsAvailableByOrganization);
    return this.http
      .post<RestAddOnsAvailableByOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(addOnsAvailableByOrganization: IAddOnsAvailableByOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(addOnsAvailableByOrganization);
    return this.http
      .put<RestAddOnsAvailableByOrganization>(
        `${this.resourceUrl}/${addOnsAvailableByOrganization.compositeId.organizationId}/${addOnsAvailableByOrganization.compositeId.entityType}/${addOnsAvailableByOrganization.compositeId.entityId}/${addOnsAvailableByOrganization.compositeId.addOnId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(addOnsAvailableByOrganization: PartialUpdateAddOnsAvailableByOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(addOnsAvailableByOrganization);
    return this.http
      .patch<RestAddOnsAvailableByOrganization>(
        `${this.resourceUrl}/${addOnsAvailableByOrganization.compositeId.organizationId}/${addOnsAvailableByOrganization.compositeId.entityType}/${addOnsAvailableByOrganization.compositeId.entityId}/${addOnsAvailableByOrganization.compositeId.addOnId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(organizationId: string, entityType: string, entityId: string, addOnId: string): Observable<EntityResponseType> {
    const data = { organizationId, entityType, entityId, addOnId };
    return this.http
      .get<RestAddOnsAvailableByOrganization>(`${this.resourceUrl}/get`, { params: data, observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAddOnsAvailableByOrganization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(addOnsAvailableByOrganization: IAddOnsAvailableByOrganization): Observable<HttpResponse<{}>> {
    const copy = this.convertDateFromClient(addOnsAvailableByOrganization);
    return this.http.delete(
      `${this.resourceUrl}/${addOnsAvailableByOrganization.compositeId.organizationId}/${addOnsAvailableByOrganization.compositeId.entityType}/${addOnsAvailableByOrganization.compositeId.entityId}/${addOnsAvailableByOrganization.compositeId.addOnId}`,
      { observe: 'response' },
    );
  }

  getAddOnsAvailableByOrganizationIdentifier(
    addOnsAvailableByOrganization: Pick<IAddOnsAvailableByOrganization, 'compositeId'>,
  ): IAddOnsAvailableByOrganizationId {
    return addOnsAvailableByOrganization.compositeId;
  }
  compareAddOnsAvailableByOrganization(
    o1: Pick<IAddOnsAvailableByOrganization, 'compositeId'> | null,
    o2: Pick<IAddOnsAvailableByOrganization, 'compositeId'> | null,
  ): boolean {
    return o1 && o2
      ? this.getAddOnsAvailableByOrganizationIdentifier(o1) === this.getAddOnsAvailableByOrganizationIdentifier(o2)
      : o1 === o2;
  }
  addAddOnsAvailableByOrganizationToCollectionIfMissing<Type extends Pick<IAddOnsAvailableByOrganization, 'compositeId'>>(
    addOnsAvailableByOrganizationCollection: Type[],
    ...addOnsAvailableByOrganizationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const addOnsAvailableByOrganizations: Type[] = addOnsAvailableByOrganizationsToCheck.filter(isPresent);
    if (addOnsAvailableByOrganizations.length > 0) {
      const addOnsAvailableByOrganizationCollectionIdentifiers = addOnsAvailableByOrganizationCollection.map(
        addOnsAvailableByOrganizationItem => this.getAddOnsAvailableByOrganizationIdentifier(addOnsAvailableByOrganizationItem),
      );
      const addOnsAvailableByOrganizationsToAdd = addOnsAvailableByOrganizations.filter(addOnsAvailableByOrganizationItem => {
        const addOnsAvailableByOrganizationIdentifier = this.getAddOnsAvailableByOrganizationIdentifier(addOnsAvailableByOrganizationItem);
        if (addOnsAvailableByOrganizationCollectionIdentifiers.includes(addOnsAvailableByOrganizationIdentifier)) {
          return false;
        }
        addOnsAvailableByOrganizationCollectionIdentifiers.push(addOnsAvailableByOrganizationIdentifier);
        return true;
      });
      return [...addOnsAvailableByOrganizationsToAdd, ...addOnsAvailableByOrganizationCollection];
    }
    return addOnsAvailableByOrganizationCollection;
  }

  protected convertDateFromClient<
    T extends IAddOnsAvailableByOrganization | NewAddOnsAvailableByOrganization | PartialUpdateAddOnsAvailableByOrganization,
  >(addOnsAvailableByOrganization: T): RestOf<T> {
    return {
      ...addOnsAvailableByOrganization,
      compositeId: {
        ...addOnsAvailableByOrganization.compositeId,
      },
      /* eslint-disable @typescript-eslint/no-unnecessary-condition */
      addOnDetailsBigInt: addOnsAvailableByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(Object.entries(addOnsAvailableByOrganization.addOnDetailsBigInt).map(([k, v]) => [k, v?.valueOf()]))
        : {},
      /* eslint-enable @typescript-eslint/no-unnecessary-condition */
    } as RestOf<T>;
  }

  protected convertDateFromServer(restAddOnsAvailableByOrganization: RestAddOnsAvailableByOrganization): IAddOnsAvailableByOrganization {
    return {
      ...restAddOnsAvailableByOrganization,
      compositeId: {
        organizationId: restAddOnsAvailableByOrganization.compositeId.organizationId,

        entityType: restAddOnsAvailableByOrganization.compositeId.entityType,

        entityId: restAddOnsAvailableByOrganization.compositeId.entityId,

        addOnId: restAddOnsAvailableByOrganization.compositeId.addOnId,
      },
      addOnDetailsBigInt: restAddOnsAvailableByOrganization.addOnDetailsBigInt
        ? // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
          Object.fromEntries(Object.entries(restAddOnsAvailableByOrganization.addOnDetailsBigInt).map(([k, v]) => [k, dayjs(v)]))
        : {},
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAddOnsAvailableByOrganization>): HttpResponse<IAddOnsAvailableByOrganization> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestAddOnsAvailableByOrganization[]>,
  ): HttpResponse<IAddOnsAvailableByOrganization[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
