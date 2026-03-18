import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { ISaathratriEntity2, ISaathratriEntity2Id, NewSaathratriEntity2 } from '../saathratri-entity-2.model';
export type PartialUpdateSaathratriEntity2 = Partial<ISaathratriEntity2> & Pick<ISaathratriEntity2, 'compositeId'>;

type RestOf<T extends ISaathratriEntity2 | NewSaathratriEntity2> = Omit<T, 'arrivalDate' | 'blogId' | 'departureDate'> & {
  compositeId: {
    arrivalDate?: number | null;
  };
  departureDate?: number | null;
};

export type RestSaathratriEntity2 = RestOf<ISaathratriEntity2>;

export type NewRestSaathratriEntity2 = RestOf<NewSaathratriEntity2>;

export type PartialUpdateRestSaathratriEntity2 = RestOf<PartialUpdateSaathratriEntity2>;

export type EntityResponseType = HttpResponse<ISaathratriEntity2>;
export type EntityArrayResponseType = HttpResponse<ISaathratriEntity2[]>;

@Injectable()
export class SaathratriEntity2sService {
  readonly saathratriEntity2sParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly saathratriEntity2sResource = httpResource<RestSaathratriEntity2[]>(() => {
    const params = this.saathratriEntity2sParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of saathratriEntity2 that have been fetched.
   */
  readonly saathratriEntity2s = computed(() =>
    (this.saathratriEntity2sResource.hasValue() ? this.saathratriEntity2sResource.value() : []).map(item =>
      this.convertValueFromServer(item),
    ),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/saathratri-entity-2-s', 'cassandrablog');

  protected convertValueFromServer(restSaathratriEntity2: RestSaathratriEntity2): ISaathratriEntity2 {
    return {
      ...restSaathratriEntity2,
      compositeId: {
        entityTypeId: restSaathratriEntity2.compositeId.entityTypeId,

        yearOfDateAdded: restSaathratriEntity2.compositeId.yearOfDateAdded,
        arrivalDate: restSaathratriEntity2.compositeId.arrivalDate ? dayjs(restSaathratriEntity2.compositeId.arrivalDate) : null,

        blogId: restSaathratriEntity2.compositeId.blogId,
      },
      departureDate: restSaathratriEntity2.departureDate ? dayjs(restSaathratriEntity2.departureDate) : null,
    };
  }
}

@Injectable({ providedIn: 'root' })
export class SaathratriEntity2Service extends SaathratriEntity2sService {
  protected readonly http = inject(HttpClient);

  create(saathratriEntity2: NewSaathratriEntity2): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(saathratriEntity2);
    return this.http
      .post<RestSaathratriEntity2>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(saathratriEntity2: ISaathratriEntity2): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(saathratriEntity2);
    return this.http
      .put<RestSaathratriEntity2>(
        `${this.resourceUrl}/${saathratriEntity2.compositeId.entityTypeId}/${saathratriEntity2.compositeId.yearOfDateAdded}/${copy.compositeId.arrivalDate}/${saathratriEntity2.compositeId.blogId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(saathratriEntity2: PartialUpdateSaathratriEntity2): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(saathratriEntity2);
    return this.http
      .patch<RestSaathratriEntity2>(
        `${this.resourceUrl}/${saathratriEntity2.compositeId.entityTypeId}/${saathratriEntity2.compositeId.yearOfDateAdded}/${copy.compositeId.arrivalDate}/${saathratriEntity2.compositeId.blogId}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(entityTypeId: string, yearOfDateAdded: number, arrivalDate: number, blogId: string): Observable<EntityResponseType> {
    const data = { entityTypeId, yearOfDateAdded, arrivalDate, blogId };
    return this.http
      .get<RestSaathratriEntity2>(`${this.resourceUrl}/get`, { params: data, observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSaathratriEntity2[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(saathratriEntity2: ISaathratriEntity2): Observable<HttpResponse<{}>> {
    const copy = this.convertValueFromClient(saathratriEntity2);
    return this.http.delete(
      `${this.resourceUrl}/${saathratriEntity2.compositeId.entityTypeId}/${saathratriEntity2.compositeId.yearOfDateAdded}/${copy.compositeId.arrivalDate}/${saathratriEntity2.compositeId.blogId}`,
      { observe: 'response' },
    );
  }

  getSaathratriEntity2Identifier(saathratriEntity2: Pick<ISaathratriEntity2, 'compositeId'>): ISaathratriEntity2Id {
    return saathratriEntity2.compositeId;
  }
  compareSaathratriEntity2(
    o1: Pick<ISaathratriEntity2, 'compositeId'> | null,
    o2: Pick<ISaathratriEntity2, 'compositeId'> | null,
  ): boolean {
    return o1 && o2 ? this.getSaathratriEntity2Identifier(o1) === this.getSaathratriEntity2Identifier(o2) : o1 === o2;
  }
  addSaathratriEntity2ToCollectionIfMissing<Type extends Pick<ISaathratriEntity2, 'compositeId'>>(
    saathratriEntity2Collection: Type[],
    ...saathratriEntity2sToCheck: (Type | null | undefined)[]
  ): Type[] {
    const saathratriEntity2s: Type[] = saathratriEntity2sToCheck.filter(isPresent);
    if (saathratriEntity2s.length > 0) {
      const saathratriEntity2CollectionIdentifiers = saathratriEntity2Collection.map(saathratriEntity2Item =>
        this.getSaathratriEntity2Identifier(saathratriEntity2Item),
      );
      const saathratriEntity2sToAdd = saathratriEntity2s.filter(saathratriEntity2Item => {
        const saathratriEntity2Identifier = this.getSaathratriEntity2Identifier(saathratriEntity2Item);
        if (saathratriEntity2CollectionIdentifiers.includes(saathratriEntity2Identifier)) {
          return false;
        }
        saathratriEntity2CollectionIdentifiers.push(saathratriEntity2Identifier);
        return true;
      });
      return [...saathratriEntity2sToAdd, ...saathratriEntity2Collection];
    }
    return saathratriEntity2Collection;
  }

  protected convertValueFromClient<T extends ISaathratriEntity2 | NewSaathratriEntity2 | PartialUpdateSaathratriEntity2>(
    saathratriEntity2: T,
  ): RestOf<T> {
    return {
      ...saathratriEntity2,
      compositeId: {
        ...saathratriEntity2.compositeId,

        arrivalDate: saathratriEntity2.compositeId.arrivalDate ? saathratriEntity2.compositeId.arrivalDate.valueOf() : null,
      },
      departureDate: saathratriEntity2.departureDate ? saathratriEntity2.departureDate.valueOf() : null,
    } as RestOf<T>;
  }

  protected convertResponseFromServer(res: HttpResponse<RestSaathratriEntity2>): HttpResponse<ISaathratriEntity2> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSaathratriEntity2[]>): HttpResponse<ISaathratriEntity2[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
