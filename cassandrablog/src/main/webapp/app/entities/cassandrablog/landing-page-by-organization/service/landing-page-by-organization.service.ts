import { HttpClient, HttpResponse, httpResource } from '@angular/common/http';
import { Injectable, computed, inject, signal } from '@angular/core';

import dayjs from 'dayjs/esm';
import { Observable, map } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
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

@Injectable()
export class LandingPageByOrganizationsService {
  readonly landingPageByOrganizationsParams = signal<
    Record<string, string | number | boolean | readonly (string | number | boolean)[]> | undefined
  >(undefined);
  readonly landingPageByOrganizationsResource = httpResource<RestLandingPageByOrganization[]>(() => {
    const params = this.landingPageByOrganizationsParams();
    if (!params) {
      return undefined;
    }
    return { url: this.resourceUrl, params };
  });
  /**
   * This signal holds the list of landingPageByOrganization that have been fetched.
   */
  readonly landingPageByOrganizations = computed(() =>
    (this.landingPageByOrganizationsResource.hasValue() ? this.landingPageByOrganizationsResource.value() : []).map(item =>
      this.convertValueFromServer(item),
    ),
  );
  protected readonly applicationConfigService = inject(ApplicationConfigService);
  protected readonly resourceUrl = this.applicationConfigService.getEndpointFor('api/landing-page-by-organizations', 'cassandrablog');

  protected convertValueFromServer(restLandingPageByOrganization: RestLandingPageByOrganization): ILandingPageByOrganization {
    return {
      ...restLandingPageByOrganization,
      detailsBigInt: restLandingPageByOrganization.detailsBigInt
        ? // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
          Object.fromEntries(Object.entries(restLandingPageByOrganization.detailsBigInt).map(([k, v]) => [k, dayjs(v)]))
        : {},
    };
  }
}

@Injectable({ providedIn: 'root' })
export class LandingPageByOrganizationService extends LandingPageByOrganizationsService {
  protected readonly http = inject(HttpClient);

  create(landingPageByOrganization: NewLandingPageByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(landingPageByOrganization);
    return this.http
      .post<RestLandingPageByOrganization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(landingPageByOrganization: ILandingPageByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(landingPageByOrganization);
    return this.http
      .put<RestLandingPageByOrganization>(
        `${this.resourceUrl}/${this.getLandingPageByOrganizationIdentifier(landingPageByOrganization)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(landingPageByOrganization: PartialUpdateLandingPageByOrganization): Observable<EntityResponseType> {
    const copy = this.convertValueFromClient(landingPageByOrganization);
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

  protected convertValueFromClient<
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

  protected convertResponseFromServer(res: HttpResponse<RestLandingPageByOrganization>): HttpResponse<ILandingPageByOrganization> {
    return res.clone({
      body: res.body ? this.convertValueFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLandingPageByOrganization[]>): HttpResponse<ILandingPageByOrganization[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertValueFromServer(item)) : null,
    });
  }
}
