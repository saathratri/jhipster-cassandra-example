import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISaathratriEntity3 } from '../saathratri-entity-3.model';
import { SaathratriEntity3Service } from '../service/saathratri-entity-3.service';

const saathratriEntity3Resolve = (route: ActivatedRouteSnapshot): Observable<null | ISaathratriEntity3> => {
  const entityType = route.params.entityType;
  const createdTimeId = route.params.createdTimeId;

  if (entityType && createdTimeId) {
    return inject(SaathratriEntity3Service)
      .find(entityType, createdTimeId)
      .pipe(
        mergeMap((saathratriEntity3: HttpResponse<ISaathratriEntity3>) => {
          if (saathratriEntity3.body) {
            return of(saathratriEntity3.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default saathratriEntity3Resolve;
