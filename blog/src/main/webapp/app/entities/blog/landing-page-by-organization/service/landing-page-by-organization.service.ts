import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILandingPageByOrganization, NewLandingPageByOrganization } from '../landing-page-by-organization.model';
export type PartialUpdateLandingPageByOrganization = Partial<ILandingPageByOrganization> &
  Pick<ILandingPageByOrganization, 'organizationId'>;

type RestOf<T extends ILandingPageByOrganization | NewLandingPageByOrganization> = Omit<T, 'detailsBigInt'> & {
  detailsText?: Record<string, string> | null;
  detailsDecimal?: Record<string, number> | null;
  detailsBoolean?: Record<string, boolean> | null;
  detailsBigInt?: Record<string, dayjs.Dayjs> | null;
};

export type RestLandingPageByOrganization = RestOf<ILandingPageByOrganization>;

export type NewRestLandingPageByOrganization = RestOf<NewLandingPageByOrganization>;

export type PartialUpdateRestLandingPageByOrganization = RestOf<PartialUpdateLandingPageByOrganization>;

export type EntityResponseType = HttpResponse<ILandingPageByOrganization>;
export type EntityArrayResponseType = HttpResponse<ILandingPageByOrganization[]>;

@Injectable({ providedIn: 'root' })
export class LandingPageByOrganizationService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/landing-page-by-organizations', 'blog');

  create(landingPageByOrganization: NewLandingPageByOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(landingPageByOrganization);
    return this.http
      .post<RestLandingPageByOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(landingPageByOrganization: ILandingPageByOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(landingPageByOrganization);
    return this.http
      .put<RestLandingPageByOrganization>(
        `${this.resourceUrl}/${this.getLandingPageByOrganizationIdentifier(landingPageByOrganization)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(landingPageByOrganization: PartialUpdateLandingPageByOrganization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(landingPageByOrganization);
    return this.http
      .patch<RestLandingPageByOrganization>(
        `${this.resourceUrl}/${this.getLandingPageByOrganizationIdentifier(landingPageByOrganization)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestLandingPageByOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLandingPageByOrganization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLandingPageByOrganizationIdentifier(landingPageByOrganization: Pick<ILandingPageByOrganization, 'organizationId'>): string {
    return landingPageByOrganization.organizationId;
  }

  compareLandingPageByOrganization(
    o1: Pick<ILandingPageByOrganization, 'organizationId'> | null,
    o2: Pick<ILandingPageByOrganization, 'organizationId'> | null,
  ): boolean {
    return o1 && o2 ? this.getLandingPageByOrganizationIdentifier(o1) === this.getLandingPageByOrganizationIdentifier(o2) : o1 === o2;
  }

  addLandingPageByOrganizationToCollectionIfMissing<Type extends Pick<ILandingPageByOrganization, 'organizationId'>>(
    landingPageByOrganizationCollection: Type[],
    ...landingPageByOrganizationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const landingPageByOrganizations: Type[] = landingPageByOrganizationsToCheck.filter(isPresent);
    if (landingPageByOrganizations.length > 0) {
      const landingPageByOrganizationCollectionIdentifiers = landingPageByOrganizationCollection.map(landingPageByOrganizationItem =>
        this.getLandingPageByOrganizationIdentifier(landingPageByOrganizationItem),
      );
      const landingPageByOrganizationsToAdd = landingPageByOrganizations.filter(landingPageByOrganizationItem => {
        const landingPageByOrganizationIdentifier = this.getLandingPageByOrganizationIdentifier(landingPageByOrganizationItem);
        if (landingPageByOrganizationCollectionIdentifiers.includes(landingPageByOrganizationIdentifier)) {
          return false;
        }
        landingPageByOrganizationCollectionIdentifiers.push(landingPageByOrganizationIdentifier);
        return true;
      });
      return [...landingPageByOrganizationsToAdd, ...landingPageByOrganizationCollection];
    }
    return landingPageByOrganizationCollection;
  }

  protected convertDateFromClient<
    T extends ILandingPageByOrganization | NewLandingPageByOrganization | PartialUpdateLandingPageByOrganization,
  >(landingPageByOrganization: T): RestOf<T> {
    return {
      ...landingPageByOrganization,
      /* eslint-disable @typescript-eslint/no-unnecessary-condition */
      detailsBigInt: landingPageByOrganization.detailsBigInt
        ? Object.fromEntries(Object.entries(landingPageByOrganization.detailsBigInt).map(([k, v]) => [k, v?.valueOf()]))
        : {},
      /* eslint-enable @typescript-eslint/no-unnecessary-condition */
    } as RestOf<T>;
  }

  protected convertDateFromServer(restLandingPageByOrganization: RestLandingPageByOrganization): ILandingPageByOrganization {
    return {
      ...restLandingPageByOrganization,
      detailsBigInt: restLandingPageByOrganization.detailsBigInt
        ? // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
          Object.fromEntries(Object.entries(restLandingPageByOrganization.detailsBigInt).map(([k, v]) => [k, dayjs(v)]))
        : {},
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLandingPageByOrganization>): HttpResponse<ILandingPageByOrganization> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLandingPageByOrganization[]>): HttpResponse<ILandingPageByOrganization[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
