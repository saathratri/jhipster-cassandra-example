import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISaathratriEntity6 } from '../saathratri-entity-6.model';
import { SaathratriEntity6Service } from '../service/saathratri-entity-6.service';

const saathratriEntity6Resolve = (route: ActivatedRouteSnapshot): Observable<null | ISaathratriEntity6> => {
  const organizationId = route.params.organizationId;
  const arrivalDate = route.params.arrivalDate;
  const accountNumber = route.params.accountNumber;
  const createdTimeId = route.params.createdTimeId;

  if (organizationId && arrivalDate && accountNumber && createdTimeId) {
    return inject(SaathratriEntity6Service)
      .find(organizationId, arrivalDate, accountNumber, createdTimeId)
      .pipe(
        mergeMap((saathratriEntity6: HttpResponse<ISaathratriEntity6>) => {
          if (saathratriEntity6.body) {
            return of(saathratriEntity6.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default saathratriEntity6Resolve;
