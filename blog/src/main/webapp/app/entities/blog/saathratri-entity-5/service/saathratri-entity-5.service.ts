import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISaathratriEntity5, ISaathratriEntity5Id, NewSaathratriEntity5 } from '../saathratri-entity-5.model';
export type PartialUpdateSaathratriEntity5 = Partial<ISaathratriEntity5> & Pick<ISaathratriEntity5, 'compositeId'>;

type RestOf<T extends ISaathratriEntity5 | NewSaathratriEntity5> = Omit<T, 'addOnDetailsBigInt'> & {
  compositeId: {};
  addOnDetailsText?: Record<string, string> | null;
  addOnDetailsDecimal?: Record<string, number> | null;
  addOnDetailsBoolean?: Record<string, boolean> | null;
  addOnDetailsBigInt?: Record<string, dayjs.Dayjs> | null;
};

export type RestSaathratriEntity5 = RestOf<ISaathratriEntity5>;

export type NewRestSaathratriEntity5 = RestOf<NewSaathratriEntity5>;

export type PartialUpdateRestSaathratriEntity5 = RestOf<PartialUpdateSaathratriEntity5>;

export type EntityResponseType = HttpResponse<ISaathratriEntity5>;
export type EntityArrayResponseType = HttpResponse<ISaathratriEntity5[]>;

@Injectable({ providedIn: 'root' })
export class SaathratriEntity5Service {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/saathratri-entity-5-s', 'blog');

  create(saathratriEntity5: NewSaathratriEntity5): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(saathratriEntity5);
    return this.http
      .post<RestSaathratriEntity5>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(saathratriEntity5: ISaathratriEntity5): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(saathratriEntity5);
    return this.http
      .put<RestSaathratriEntity5>(
        `${this.resourceUrl}/${saathratriEntity5.compositeId.organizationId}/${saathratriEntity5.compositeId.entityType}/${saathratriEntity5.compositeId.entityId}/${saathratriEntity5.compositeId.addOnId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(saathratriEntity5: PartialUpdateSaathratriEntity5): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(saathratriEntity5);
    return this.http
      .patch<RestSaathratriEntity5>(
        `${this.resourceUrl}/${saathratriEntity5.compositeId.organizationId}/${saathratriEntity5.compositeId.entityType}/${saathratriEntity5.compositeId.entityId}/${saathratriEntity5.compositeId.addOnId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(organizationId: string, entityType: string, entityId: string, addOnId: string): Observable<EntityResponseType> {
    const data = { organizationId, entityType, entityId, addOnId };
    return this.http
      .get<RestSaathratriEntity5>(`${this.resourceUrl}/get`, { params: data, observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSaathratriEntity5[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(saathratriEntity5: ISaathratriEntity5): Observable<HttpResponse<{}>> {
    const copy = this.convertDateFromClient(saathratriEntity5);
    return this.http.delete(
      `${this.resourceUrl}/${saathratriEntity5.compositeId.organizationId}/${saathratriEntity5.compositeId.entityType}/${saathratriEntity5.compositeId.entityId}/${saathratriEntity5.compositeId.addOnId}`,
      { observe: 'response' },
    );
  }

  getSaathratriEntity5Identifier(saathratriEntity5: Pick<ISaathratriEntity5, 'compositeId'>): ISaathratriEntity5Id {
    return saathratriEntity5.compositeId;
  }
  compareSaathratriEntity5(
    o1: Pick<ISaathratriEntity5, 'compositeId'> | null,
    o2: Pick<ISaathratriEntity5, 'compositeId'> | null,
  ): boolean {
    return o1 && o2 ? this.getSaathratriEntity5Identifier(o1) === this.getSaathratriEntity5Identifier(o2) : o1 === o2;
  }
  addSaathratriEntity5ToCollectionIfMissing<Type extends Pick<ISaathratriEntity5, 'compositeId'>>(
    saathratriEntity5Collection: Type[],
    ...saathratriEntity5sToCheck: (Type | null | undefined)[]
  ): Type[] {
    const saathratriEntity5s: Type[] = saathratriEntity5sToCheck.filter(isPresent);
    if (saathratriEntity5s.length > 0) {
      const saathratriEntity5CollectionIdentifiers = saathratriEntity5Collection.map(saathratriEntity5Item =>
        this.getSaathratriEntity5Identifier(saathratriEntity5Item),
      );
      const saathratriEntity5sToAdd = saathratriEntity5s.filter(saathratriEntity5Item => {
        const saathratriEntity5Identifier = this.getSaathratriEntity5Identifier(saathratriEntity5Item);
        if (saathratriEntity5CollectionIdentifiers.includes(saathratriEntity5Identifier)) {
          return false;
        }
        saathratriEntity5CollectionIdentifiers.push(saathratriEntity5Identifier);
        return true;
      });
      return [...saathratriEntity5sToAdd, ...saathratriEntity5Collection];
    }
    return saathratriEntity5Collection;
  }

  protected convertDateFromClient<T extends ISaathratriEntity5 | NewSaathratriEntity5 | PartialUpdateSaathratriEntity5>(
    saathratriEntity5: T,
  ): RestOf<T> {
    return {
      ...saathratriEntity5,
      compositeId: {
        ...saathratriEntity5.compositeId,
      },

      addOnDetailsBigInt: saathratriEntity5.addOnDetailsBigInt
        ? // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
          Object.fromEntries(Object.entries(saathratriEntity5.addOnDetailsBigInt).map(([k, v]) => [k, v?.valueOf()]))
        : {},
    } as RestOf<T>;
  }

  protected convertDateFromServer(restSaathratriEntity5: RestSaathratriEntity5): ISaathratriEntity5 {
    return {
      ...restSaathratriEntity5,
      compositeId: {
        organizationId: restSaathratriEntity5.compositeId.organizationId,

        entityType: restSaathratriEntity5.compositeId.entityType,

        entityId: restSaathratriEntity5.compositeId.entityId,

        addOnId: restSaathratriEntity5.compositeId.addOnId,
      },
      addOnDetailsBigInt: restSaathratriEntity5.addOnDetailsBigInt
        ? // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
          Object.fromEntries(Object.entries(restSaathratriEntity5.addOnDetailsBigInt).map(([k, v]) => [k, dayjs(v)]))
        : {},
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSaathratriEntity5>): HttpResponse<ISaathratriEntity5> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSaathratriEntity5[]>): HttpResponse<ISaathratriEntity5[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
