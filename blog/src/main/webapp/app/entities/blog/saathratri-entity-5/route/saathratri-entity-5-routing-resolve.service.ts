import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISaathratriEntity5 } from '../saathratri-entity-5.model';
import { SaathratriEntity5Service } from '../service/saathratri-entity-5.service';

const saathratriEntity5Resolve = (route: ActivatedRouteSnapshot): Observable<null | ISaathratriEntity5> => {
  const organizationId = route.params.organizationId;
  const entityType = route.params.entityType;
  const entityId = route.params.entityId;
  const addOnId = route.params.addOnId;

  if (organizationId && entityType && entityId && addOnId) {
    return inject(SaathratriEntity5Service)
      .find(organizationId, entityType, entityId, addOnId)
      .pipe(
        mergeMap((saathratriEntity5: HttpResponse<ISaathratriEntity5>) => {
          if (saathratriEntity5.body) {
            return of(saathratriEntity5.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default saathratriEntity5Resolve;
