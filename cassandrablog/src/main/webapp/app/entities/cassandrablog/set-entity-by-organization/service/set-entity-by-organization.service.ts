import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
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

@Injectable()
export class SetEntityByOrganizationsService {
  readonly setEntityByOrganizationsParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly setEntityByOrganizationsResource = httpResource<RestSetEntityByOrganization[]>(() => {
    const params = this.setEntityByOrganizationsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of setEntityByOrganization that have been fetched.
   */
  readonly setEntityByOrganizations = computed(() =>
    (this.setEntityByOrganizationsResource.hasValue() ? this.setEntityByOrganizationsResource.value() : []).map(item =>
      this.convertValueFromServer(item),
    ),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/set-entity-by-organizations', 'cassandrablog');

  protected convertValueFromServer(restSetEntityByOrganization: RestSetEntityByOrganization): ISetEntityByOrganization {
    return {
      ...restSetEntityByOrganization,
      tags: restSetEntityByOrganization.tags ? new Set(restSetEntityByOrganization.tags) : new Set<string>(),
    };
  }
}

@Injectable({ providedIn: 'root' })
export class SetEntityByOrganizationService extends SetEntityByOrganizationsService {
  protected readonly http = inject(HttpClient);

  create(setEntityByOrganization: NewSetEntityByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(setEntityByOrganization);
    return this.http
      .post<RestSetEntityByOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(setEntityByOrganization: ISetEntityByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(setEntityByOrganization);
    return this.http
      .put<RestSetEntityByOrganization>(`${this.resourceUrl}/${this.getSetEntityByOrganizationIdentifier(setEntityByOrganization)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(setEntityByOrganization: PartialUpdateSetEntityByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(setEntityByOrganization);
    return this.http
      .patch<RestSetEntityByOrganization>(
        `${this.resourceUrl}/${this.getSetEntityByOrganizationIdentifier(setEntityByOrganization)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestSetEntityByOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSetEntityByOrganization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  querySlice(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSetEntityByOrganization[]>(`${this.resourceUrl}/slice`, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
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

  protected convertValueFromClient<T extends ISetEntityByOrganization | NewSetEntityByOrganization | PartialUpdateSetEntityByOrganization>(
    setEntityByOrganization: T,
  ): RestOf<T> {
    return {
      ...setEntityByOrganization,
      tags: setEntityByOrganization.tags ? Array.from(setEntityByOrganization.tags) : [],
    } as RestOf<T>;
  }

  protected convertResponseFromServer(res: HttpResponse<RestSetEntityByOrganization>): HttpResponse<ISetEntityByOrganization> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSetEntityByOrganization[]>): HttpResponse<ISetEntityByOrganization[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
