import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAddOnsSelectedByOrganization } from '../add-ons-selected-by-organization.model';
import { AddOnsSelectedByOrganizationService } from '../service/add-ons-selected-by-organization.service';

const addOnsSelectedByOrganizationResolve = (route: ActivatedRouteSnapshot): Observable<null | IAddOnsSelectedByOrganization> => {
  const organizationId = route.params.organizationId;
  const arrivalDate = route.params.arrivalDate;
  const accountNumber = route.params.accountNumber;
  const createdTimeId = route.params.createdTimeId;

  if (organizationId && arrivalDate && accountNumber && createdTimeId) {
    return inject(AddOnsSelectedByOrganizationService)
      .find(organizationId, arrivalDate, accountNumber, createdTimeId)
      .pipe(
        mergeMap((addOnsSelectedByOrganization: HttpResponse<IAddOnsSelectedByOrganization>) => {
          if (addOnsSelectedByOrganization.body) {
            return of(addOnsSelectedByOrganization.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default addOnsSelectedByOrganizationResolve;
