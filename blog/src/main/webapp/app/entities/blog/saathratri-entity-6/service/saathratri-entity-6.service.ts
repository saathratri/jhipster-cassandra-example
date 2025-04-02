import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISaathratriEntity6, ISaathratriEntity6Id, NewSaathratriEntity6 } from '../saathratri-entity-6.model';
export type PartialUpdateSaathratriEntity6 = Partial<ISaathratriEntity6> & Pick<ISaathratriEntity6, 'compositeId'>;

type RestOf<T extends ISaathratriEntity6 | NewSaathratriEntity6> = Omit<T, 'arrivalDate' | 'departureDate' | 'addOnDetailsBigInt'> & {
  compositeId: {
    arrivalDate?: number | null;
  };
  departureDate?: number | null;
  addOnDetailsText?: Record<string, string> | null;
  addOnDetailsDecimal?: Record<string, number> | null;
  addOnDetailsBoolean?: Record<string, boolean> | null;
  addOnDetailsBigInt?: Record<string, dayjs.Dayjs> | null;
};

export type RestSaathratriEntity6 = RestOf<ISaathratriEntity6>;

export type NewRestSaathratriEntity6 = RestOf<NewSaathratriEntity6>;

export type PartialUpdateRestSaathratriEntity6 = RestOf<PartialUpdateSaathratriEntity6>;

export type EntityResponseType = HttpResponse<ISaathratriEntity6>;
export type EntityArrayResponseType = HttpResponse<ISaathratriEntity6[]>;

@Injectable({ providedIn: 'root' })
export class SaathratriEntity6Service {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/saathratri-entity-6-s', 'blog');

  create(saathratriEntity6: NewSaathratriEntity6): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(saathratriEntity6);
    return this.http
      .post<RestSaathratriEntity6>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(saathratriEntity6: ISaathratriEntity6): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(saathratriEntity6);
    return this.http
      .put<RestSaathratriEntity6>(
        `${this.resourceUrl}/${saathratriEntity6.compositeId.organizationId}/${copy.compositeId.arrivalDate}/${saathratriEntity6.compositeId.accountNumber}/${saathratriEntity6.compositeId.createdTimeId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(saathratriEntity6: PartialUpdateSaathratriEntity6): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(saathratriEntity6);
    return this.http
      .patch<RestSaathratriEntity6>(
        `${this.resourceUrl}/${saathratriEntity6.compositeId.organizationId}/${copy.compositeId.arrivalDate}/${saathratriEntity6.compositeId.accountNumber}/${saathratriEntity6.compositeId.createdTimeId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(organizationId: string, arrivalDate: number, accountNumber: string, createdTimeId: string): Observable<EntityResponseType> {
    const data = { organizationId, arrivalDate, accountNumber, createdTimeId };
    return this.http
      .get<RestSaathratriEntity6>(`${this.resourceUrl}/get`, { params: data, observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSaathratriEntity6[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(saathratriEntity6: ISaathratriEntity6): Observable<HttpResponse<{}>> {
    const copy = this.convertDateFromClient(saathratriEntity6);
    return this.http.delete(
      `${this.resourceUrl}/${saathratriEntity6.compositeId.organizationId}/${copy.compositeId.arrivalDate}/${saathratriEntity6.compositeId.accountNumber}/${saathratriEntity6.compositeId.createdTimeId}`,
      { observe: 'response' },
    );
  }

  getSaathratriEntity6Identifier(saathratriEntity6: Pick<ISaathratriEntity6, 'compositeId'>): ISaathratriEntity6Id {
    return saathratriEntity6.compositeId;
  }
  compareSaathratriEntity6(
    o1: Pick<ISaathratriEntity6, 'compositeId'> | null,
    o2: Pick<ISaathratriEntity6, 'compositeId'> | null,
  ): boolean {
    return o1 && o2 ? this.getSaathratriEntity6Identifier(o1) === this.getSaathratriEntity6Identifier(o2) : o1 === o2;
  }
  addSaathratriEntity6ToCollectionIfMissing<Type extends Pick<ISaathratriEntity6, 'compositeId'>>(
    saathratriEntity6Collection: Type[],
    ...saathratriEntity6sToCheck: (Type | null | undefined)[]
  ): Type[] {
    const saathratriEntity6s: Type[] = saathratriEntity6sToCheck.filter(isPresent);
    if (saathratriEntity6s.length > 0) {
      const saathratriEntity6CollectionIdentifiers = saathratriEntity6Collection.map(saathratriEntity6Item =>
        this.getSaathratriEntity6Identifier(saathratriEntity6Item),
      );
      const saathratriEntity6sToAdd = saathratriEntity6s.filter(saathratriEntity6Item => {
        const saathratriEntity6Identifier = this.getSaathratriEntity6Identifier(saathratriEntity6Item);
        if (saathratriEntity6CollectionIdentifiers.includes(saathratriEntity6Identifier)) {
          return false;
        }
        saathratriEntity6CollectionIdentifiers.push(saathratriEntity6Identifier);
        return true;
      });
      return [...saathratriEntity6sToAdd, ...saathratriEntity6Collection];
    }
    return saathratriEntity6Collection;
  }

  protected convertDateFromClient<T extends ISaathratriEntity6 | NewSaathratriEntity6 | PartialUpdateSaathratriEntity6>(
    saathratriEntity6: T,
  ): RestOf<T> {
    return {
      ...saathratriEntity6,
      compositeId: {
        ...saathratriEntity6.compositeId,

        arrivalDate: saathratriEntity6.compositeId.arrivalDate ? saathratriEntity6.compositeId.arrivalDate.valueOf() : null,
      },
      departureDate: saathratriEntity6.departureDate ? saathratriEntity6.departureDate.valueOf() : null,

      addOnDetailsBigInt: saathratriEntity6.addOnDetailsBigInt
        ? // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
          Object.fromEntries(Object.entries(saathratriEntity6.addOnDetailsBigInt).map(([k, v]) => [k, v?.valueOf()]))
        : {},
    } as RestOf<T>;
  }

  protected convertDateFromServer(restSaathratriEntity6: RestSaathratriEntity6): ISaathratriEntity6 {
    return {
      ...restSaathratriEntity6,
      compositeId: {
        organizationId: restSaathratriEntity6.compositeId.organizationId,
        arrivalDate: restSaathratriEntity6.compositeId.arrivalDate ? dayjs(restSaathratriEntity6.compositeId.arrivalDate) : null,

        accountNumber: restSaathratriEntity6.compositeId.accountNumber,

        createdTimeId: restSaathratriEntity6.compositeId.createdTimeId,
      },
      departureDate: restSaathratriEntity6.departureDate ? dayjs(restSaathratriEntity6.departureDate) : null,
      addOnDetailsBigInt: restSaathratriEntity6.addOnDetailsBigInt
        ? // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
          Object.fromEntries(Object.entries(restSaathratriEntity6.addOnDetailsBigInt).map(([k, v]) => [k, dayjs(v)]))
        : {},
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSaathratriEntity6>): HttpResponse<ISaathratriEntity6> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSaathratriEntity6[]>): HttpResponse<ISaathratriEntity6[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
