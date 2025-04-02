import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISaathratriEntity, NewSaathratriEntity } from '../saathratri-entity.model';
export type PartialUpdateSaathratriEntity = Partial<ISaathratriEntity> & Pick<ISaathratriEntity, 'entityId'>;

export type EntityResponseType = HttpResponse<ISaathratriEntity>;
export type EntityArrayResponseType = HttpResponse<ISaathratriEntity[]>;

@Injectable({ providedIn: 'root' })
export class SaathratriEntityService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/saathratri-entities', 'blog');

  create(saathratriEntity: NewSaathratriEntity): Observable<EntityResponseType> {
    return this.http.post<ISaathratriEntity>(this.resourceUrl, saathratriEntity, { observe: 'response' });
  }

  update(saathratriEntity: ISaathratriEntity): Observable<EntityResponseType> {
    return this.http.put<ISaathratriEntity>(
      `${this.resourceUrl}/${this.getSaathratriEntityIdentifier(saathratriEntity)}`,
      saathratriEntity,
      { observe: 'response' },
    );
  }

  partialUpdate(saathratriEntity: PartialUpdateSaathratriEntity): Observable<EntityResponseType> {
    return this.http.patch<ISaathratriEntity>(
      `${this.resourceUrl}/${this.getSaathratriEntityIdentifier(saathratriEntity)}`,
      saathratriEntity,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ISaathratriEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISaathratriEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSaathratriEntityIdentifier(saathratriEntity: Pick<ISaathratriEntity, 'entityId'>): string {
    return saathratriEntity.entityId;
  }

  compareSaathratriEntity(o1: Pick<ISaathratriEntity, 'entityId'> | null, o2: Pick<ISaathratriEntity, 'entityId'> | null): boolean {
    return o1 && o2 ? this.getSaathratriEntityIdentifier(o1) === this.getSaathratriEntityIdentifier(o2) : o1 === o2;
  }

  addSaathratriEntityToCollectionIfMissing<Type extends Pick<ISaathratriEntity, 'entityId'>>(
    saathratriEntityCollection: Type[],
    ...saathratriEntitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const saathratriEntities: Type[] = saathratriEntitiesToCheck.filter(isPresent);
    if (saathratriEntities.length > 0) {
      const saathratriEntityCollectionIdentifiers = saathratriEntityCollection.map(saathratriEntityItem =>
        this.getSaathratriEntityIdentifier(saathratriEntityItem),
      );
      const saathratriEntitiesToAdd = saathratriEntities.filter(saathratriEntityItem => {
        const saathratriEntityIdentifier = this.getSaathratriEntityIdentifier(saathratriEntityItem);
        if (saathratriEntityCollectionIdentifiers.includes(saathratriEntityIdentifier)) {
          return false;
        }
        saathratriEntityCollectionIdentifiers.push(saathratriEntityIdentifier);
        return true;
      });
      return [...saathratriEntitiesToAdd, ...saathratriEntityCollection];
    }
    return saathratriEntityCollection;
  }
}
