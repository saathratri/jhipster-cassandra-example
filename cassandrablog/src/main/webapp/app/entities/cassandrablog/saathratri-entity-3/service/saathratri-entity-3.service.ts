import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ISaathratriEntity3, ISaathratriEntity3Id, NewSaathratriEntity3 } from '../saathratri-entity-3.model';
export type PartialUpdateSaathratriEntity3 = Partial<ISaathratriEntity3> & Pick<ISaathratriEntity3, 'compositeId'>;

type RestOf<T extends ISaathratriEntity3 | NewSaathratriEntity3> = Omit<T, 'createdTimeId' | 'departureDate' | 'tags'> & {
  compositeId: {};
  departureDate?: number | null;
  tags?: Set<string> | null;
};

export type RestSaathratriEntity3 = RestOf<ISaathratriEntity3>;

export type NewRestSaathratriEntity3 = RestOf<NewSaathratriEntity3>;

export type PartialUpdateRestSaathratriEntity3 = RestOf<PartialUpdateSaathratriEntity3>;

export type EntityResponseType = HttpResponse<ISaathratriEntity3>;
export type EntityArrayResponseType = HttpResponse<ISaathratriEntity3[]>;

@Injectable()
export class SaathratriEntity3sService {
  readonly saathratriEntity3sParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly saathratriEntity3sResource = httpResource<RestSaathratriEntity3[]>(() => {
    const params = this.saathratriEntity3sParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of saathratriEntity3 that have been fetched.
   */
  readonly saathratriEntity3s = computed(() =>
    (this.saathratriEntity3sResource.hasValue() ? this.saathratriEntity3sResource.value() : []).map(item =>
      this.convertValueFromServer(item),
    ),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/saathratri-entity-3-s', 'cassandrablog');

  protected convertValueFromServer(restSaathratriEntity3: RestSaathratriEntity3): ISaathratriEntity3 {
    return {
      ...restSaathratriEntity3,
      compositeId: {
        entityType: restSaathratriEntity3.compositeId.entityType,

        createdTimeId: restSaathratriEntity3.compositeId.createdTimeId,
      },
      departureDate: restSaathratriEntity3.departureDate ? dayjs(restSaathratriEntity3.departureDate) : null,
      tags: restSaathratriEntity3.tags ? new Set(restSaathratriEntity3.tags) : new Set<string>(),
    };
  }
}

@Injectable({ providedIn: 'root' })
export class SaathratriEntity3Service extends SaathratriEntity3sService {
  protected readonly http = inject(HttpClient);

  create(saathratriEntity3: NewSaathratriEntity3): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(saathratriEntity3);
    return this.http
      .post<RestSaathratriEntity3>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(saathratriEntity3: ISaathratriEntity3): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(saathratriEntity3);
    return this.http
      .put<RestSaathratriEntity3>(
        `${this.resourceUrl}/${saathratriEntity3.compositeId.entityType}/${saathratriEntity3.compositeId.createdTimeId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(saathratriEntity3: PartialUpdateSaathratriEntity3): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(saathratriEntity3);
    return this.http
      .patch<RestSaathratriEntity3>(
        `${this.resourceUrl}/${saathratriEntity3.compositeId.entityType}/${saathratriEntity3.compositeId.createdTimeId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(entityType: string, createdTimeId: string): Observable<EntityResponseType> {
    const data = { entityType, createdTimeId };
    return this.http
      .get<RestSaathratriEntity3>(`${this.resourceUrl}/get`, { params: data, observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSaathratriEntity3[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(saathratriEntity3: ISaathratriEntity3): Observable<HttpResponse<{}>> {
    const copy = this.convertValueFromClient(saathratriEntity3);
    return this.http.delete(
      `${this.resourceUrl}/${saathratriEntity3.compositeId.entityType}/${saathratriEntity3.compositeId.createdTimeId}`,
      { observe: 'response' },
    );
  }

  getSaathratriEntity3Identifier(saathratriEntity3: Pick<ISaathratriEntity3, 'compositeId'>): ISaathratriEntity3Id {
    return saathratriEntity3.compositeId;
  }
  compareSaathratriEntity3(
    o1: Pick<ISaathratriEntity3, 'compositeId'> | null,
    o2: Pick<ISaathratriEntity3, 'compositeId'> | null,
  ): boolean {
    return o1 && o2 ? this.getSaathratriEntity3Identifier(o1) === this.getSaathratriEntity3Identifier(o2) : o1 === o2;
  }
  addSaathratriEntity3ToCollectionIfMissing<Type extends Pick<ISaathratriEntity3, 'compositeId'>>(
    saathratriEntity3Collection: Type[],
    ...saathratriEntity3sToCheck: (Type | null | undefined)[]
  ): Type[] {
    const saathratriEntity3s: Type[] = saathratriEntity3sToCheck.filter(isPresent);
    if (saathratriEntity3s.length > 0) {
      const saathratriEntity3CollectionIdentifiers = saathratriEntity3Collection.map(saathratriEntity3Item =>
        this.getSaathratriEntity3Identifier(saathratriEntity3Item),
      );
      const saathratriEntity3sToAdd = saathratriEntity3s.filter(saathratriEntity3Item => {
        const saathratriEntity3Identifier = this.getSaathratriEntity3Identifier(saathratriEntity3Item);
        if (saathratriEntity3CollectionIdentifiers.includes(saathratriEntity3Identifier)) {
          return false;
        }
        saathratriEntity3CollectionIdentifiers.push(saathratriEntity3Identifier);
        return true;
      });
      return [...saathratriEntity3sToAdd, ...saathratriEntity3Collection];
    }
    return saathratriEntity3Collection;
  }

  protected convertValueFromClient<T extends ISaathratriEntity3 | NewSaathratriEntity3 | PartialUpdateSaathratriEntity3>(
    saathratriEntity3: T,
  ): RestOf<T> {
    return {
      ...saathratriEntity3,
      compositeId: {
        ...saathratriEntity3.compositeId,
      },
      departureDate: saathratriEntity3.departureDate ? saathratriEntity3.departureDate.valueOf() : null,
      tags: saathratriEntity3.tags ? Array.from(saathratriEntity3.tags) : [],
    } as RestOf<T>;
  }

  protected convertResponseFromServer(res: HttpResponse<RestSaathratriEntity3>): HttpResponse<ISaathratriEntity3> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSaathratriEntity3[]>): HttpResponse<ISaathratriEntity3[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
