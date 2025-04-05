import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISetEntityByOrganization, NewSetEntityByOrganization } from '../set-entity-by-organization.model';
export type PartialUpdateSetEntityByOrganization = Partial<ISetEntityByOrganization> & Pick<ISetEntityByOrganization, 'organizationId'>;

type RestOf<T extends ISetEntityByOrganization | NewSetEntityByOrganization> = Omit<T, 'tags'> & {
  tags?: Set<string> | null;
};

export type RestSetEntityByOrganization = RestOf<ISetEntityByOrganization>;

export type NewRestSetEntityByOrganization = RestOf<NewSetEntityByOrganization>;

export type PartialUpdateRestSetEntityByOrganization = RestOf<PartialUpdateSetEntityByOrganization>;

export type EntityResponseType = HttpResponse<ISetEntityByOrganization>;
export type EntityArrayResponseType = HttpResponse<ISetEntityByOrganization[]>;

@Injectable({ providedIn: 'root' })
export class SetEntityByOrganizationService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/set-entity-by-organizations', 'blog');

  create(setEntityByOrganization: NewSetEntityByOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(setEntityByOrganization);
    return this.http.post<RestSetEntityByOrganization>(this.resourceUrl, copy, { observe: 'response' });
  }

  update(setEntityByOrganization: ISetEntityByOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(setEntityByOrganization);
    return this.http.put<RestSetEntityByOrganization>(
      `${this.resourceUrl}/${this.getSetEntityByOrganizationIdentifier(setEntityByOrganization)}`,
      copy,
      { observe: 'response' },
    );
  }

  partialUpdate(setEntityByOrganization: PartialUpdateSetEntityByOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(setEntityByOrganization);
    return this.http.patch<RestSetEntityByOrganization>(
      `${this.resourceUrl}/${this.getSetEntityByOrganizationIdentifier(setEntityByOrganization)}`,
      copy,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<RestSetEntityByOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<RestSetEntityByOrganization[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSetEntityByOrganizationIdentifier(setEntityByOrganization: Pick<ISetEntityByOrganization, 'organizationId'>): string {
    return setEntityByOrganization.organizationId;
  }

  compareSetEntityByOrganization(
    o1: Pick<ISetEntityByOrganization, 'organizationId'> | null,
    o2: Pick<ISetEntityByOrganization, 'organizationId'> | null,
  ): boolean {
    return o1 && o2 ? this.getSetEntityByOrganizationIdentifier(o1) === this.getSetEntityByOrganizationIdentifier(o2) : o1 === o2;
  }

  addSetEntityByOrganizationToCollectionIfMissing<Type extends Pick<ISetEntityByOrganization, 'organizationId'>>(
    setEntityByOrganizationCollection: Type[],
    ...setEntityByOrganizationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const setEntityByOrganizations: Type[] = setEntityByOrganizationsToCheck.filter(isPresent);
    if (setEntityByOrganizations.length > 0) {
      const setEntityByOrganizationCollectionIdentifiers = setEntityByOrganizationCollection.map(setEntityByOrganizationItem =>
        this.getSetEntityByOrganizationIdentifier(setEntityByOrganizationItem),
      );
      const setEntityByOrganizationsToAdd = setEntityByOrganizations.filter(setEntityByOrganizationItem => {
        const setEntityByOrganizationIdentifier = this.getSetEntityByOrganizationIdentifier(setEntityByOrganizationItem);
        if (setEntityByOrganizationCollectionIdentifiers.includes(setEntityByOrganizationIdentifier)) {
          return false;
        }
        setEntityByOrganizationCollectionIdentifiers.push(setEntityByOrganizationIdentifier);
        return true;
      });
      return [...setEntityByOrganizationsToAdd, ...setEntityByOrganizationCollection];
    }
    return setEntityByOrganizationCollection;
  }

  protected convertDateFromClient<T extends ISetEntityByOrganization | NewSetEntityByOrganization | PartialUpdateSetEntityByOrganization>(
    setEntityByOrganization: T,
  ): RestOf<T> {
    return {
      ...setEntityByOrganization,
      tags: setEntityByOrganization.tags ? Array.from(setEntityByOrganization.tags) : [],
    } as RestOf<T>;
  }

  protected convertDateFromServer(restSetEntityByOrganization: RestSetEntityByOrganization): ISetEntityByOrganization {
    return {
      ...restSetEntityByOrganization,
      tags: restSetEntityByOrganization.tags ? new Set(restSetEntityByOrganization.tags) : new Set<string>(),
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSetEntityByOrganization>): HttpResponse<ISetEntityByOrganization> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSetEntityByOrganization[]>): HttpResponse<ISetEntityByOrganization[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
