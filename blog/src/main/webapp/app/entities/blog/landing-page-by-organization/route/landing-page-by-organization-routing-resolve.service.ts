import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILandingPageByOrganization } from '../landing-page-by-organization.model';
import { LandingPageByOrganizationService } from '../service/landing-page-by-organization.service';

const landingPageByOrganizationResolve = (route: ActivatedRouteSnapshot): Observable<null | ILandingPageByOrganization> => {
  const id = route.params.organizationId;

  if (id) {
    return inject(LandingPageByOrganizationService)
      .find(id)
      .pipe(
        mergeMap((landingPageByOrganization: HttpResponse<ILandingPageByOrganization>) => {
          if (landingPageByOrganization.body) {
            return of(landingPageByOrganization.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default landingPageByOrganizationResolve;
