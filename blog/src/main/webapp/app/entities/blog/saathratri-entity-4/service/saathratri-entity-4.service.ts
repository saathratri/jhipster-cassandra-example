import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISaathratriEntity4, ISaathratriEntity4Id, NewSaathratriEntity4 } from '../saathratri-entity-4.model';
export type PartialUpdateSaathratriEntity4 = Partial<ISaathratriEntity4> & Pick<ISaathratriEntity4, 'compositeId'>;

export type EntityResponseType = HttpResponse<ISaathratriEntity4>;
export type EntityArrayResponseType = HttpResponse<ISaathratriEntity4[]>;

@Injectable({ providedIn: 'root' })
export class SaathratriEntity4Service {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/saathratri-entity-4-s', 'blog');

  create(saathratriEntity4: NewSaathratriEntity4): Observable<EntityResponseType> {
    return this.http.post<ISaathratriEntity4>(this.resourceUrl, saathratriEntity4, { observe: 'response' });
  }

  update(saathratriEntity4: ISaathratriEntity4): Observable<EntityResponseType> {
    return this.http.put<ISaathratriEntity4>(
      `${this.resourceUrl}/${saathratriEntity4.compositeId.organizationId}/${saathratriEntity4.compositeId.attributeKey}`,
      saathratriEntity4,
      { observe: 'response' },
    );
  }

  partialUpdate(saathratriEntity4: PartialUpdateSaathratriEntity4): Observable<EntityResponseType> {
    return this.http.patch<ISaathratriEntity4>(
      `${this.resourceUrl}/${saathratriEntity4.compositeId.organizationId}/${saathratriEntity4.compositeId.attributeKey}`,
      saathratriEntity4,
      { observe: 'response' },
    );
  }

  find(organizationId: string, attributeKey: string): Observable<EntityResponseType> {
    const data = { organizationId, attributeKey };
    return this.http.get<ISaathratriEntity4>(`${this.resourceUrl}/get`, { params: data, observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISaathratriEntity4[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(saathratriEntity4: ISaathratriEntity4): Observable<HttpResponse<{}>> {
    return this.http.delete(
      `${this.resourceUrl}/${saathratriEntity4.compositeId.organizationId}/${saathratriEntity4.compositeId.attributeKey}`,
      { observe: 'response' },
    );
  }

  getSaathratriEntity4Identifier(saathratriEntity4: Pick<ISaathratriEntity4, 'compositeId'>): ISaathratriEntity4Id {
    return saathratriEntity4.compositeId;
  }
  compareSaathratriEntity4(
    o1: Pick<ISaathratriEntity4, 'compositeId'> | null,
    o2: Pick<ISaathratriEntity4, 'compositeId'> | null,
  ): boolean {
    return o1 && o2 ? this.getSaathratriEntity4Identifier(o1) === this.getSaathratriEntity4Identifier(o2) : o1 === o2;
  }
  addSaathratriEntity4ToCollectionIfMissing<Type extends Pick<ISaathratriEntity4, 'compositeId'>>(
    saathratriEntity4Collection: Type[],
    ...saathratriEntity4sToCheck: (Type | null | undefined)[]
  ): Type[] {
    const saathratriEntity4s: Type[] = saathratriEntity4sToCheck.filter(isPresent);
    if (saathratriEntity4s.length > 0) {
      const saathratriEntity4CollectionIdentifiers = saathratriEntity4Collection.map(saathratriEntity4Item =>
        this.getSaathratriEntity4Identifier(saathratriEntity4Item),
      );
      const saathratriEntity4sToAdd = saathratriEntity4s.filter(saathratriEntity4Item => {
        const saathratriEntity4Identifier = this.getSaathratriEntity4Identifier(saathratriEntity4Item);
        if (saathratriEntity4CollectionIdentifiers.includes(saathratriEntity4Identifier)) {
          return false;
        }
        saathratriEntity4CollectionIdentifiers.push(saathratriEntity4Identifier);
        return true;
      });
      return [...saathratriEntity4sToAdd, ...saathratriEntity4Collection];
    }
    return saathratriEntity4Collection;
  }
}
