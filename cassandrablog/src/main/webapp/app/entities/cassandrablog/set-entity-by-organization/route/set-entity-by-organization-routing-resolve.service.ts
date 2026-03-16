import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { SetEntityByOrganizationService } from '../service/set-entity-by-organization.service';
import { ISetEntityByOrganization } from '../set-entity-by-organization.model';

const setEntityByOrganizationResolve = (route: ActivatedRouteSnapshot): Observable<null | ISetEntityByOrganization> => {
  const id = route.params.organizationId;

  if (id) {
    return inject(SetEntityByOrganizationService)
      .find(id)
      .pipe(
        mergeMap((setEntityByOrganization: HttpResponse<ISetEntityByOrganization>) => {
          if (setEntityByOrganization.body) {
            return of(setEntityByOrganization.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default setEntityByOrganizationResolve;
