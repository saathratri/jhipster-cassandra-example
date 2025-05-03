import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAddOnsAvailableByOrganization } from '../add-ons-available-by-organization.model';
import { AddOnsAvailableByOrganizationService } from '../service/add-ons-available-by-organization.service';

const addOnsAvailableByOrganizationResolve = (route: ActivatedRouteSnapshot): Observable<null | IAddOnsAvailableByOrganization> => {
  const organizationId = route.params.organizationId;
  const entityType = route.params.entityType;
  const entityId = route.params.entityId;
  const addOnId = route.params.addOnId;

  if (organizationId && entityType && entityId && addOnId) {
    return inject(AddOnsAvailableByOrganizationService)
      .find(organizationId, entityType, entityId, addOnId)
      .pipe(
        mergeMap((addOnsAvailableByOrganization: HttpResponse<IAddOnsAvailableByOrganization>) => {
          if (addOnsAvailableByOrganization.body) {
            return of(addOnsAvailableByOrganization.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default addOnsAvailableByOrganizationResolve;
