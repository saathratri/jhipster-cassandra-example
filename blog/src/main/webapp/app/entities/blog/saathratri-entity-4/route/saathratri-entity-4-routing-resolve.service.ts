import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISaathratriEntity4 } from '../saathratri-entity-4.model';
import { SaathratriEntity4Service } from '../service/saathratri-entity-4.service';

const saathratriEntity4Resolve = (route: ActivatedRouteSnapshot): Observable<null | ISaathratriEntity4> => {
  const organizationId = route.params.organizationId;
  const attributeKey = route.params.attributeKey;

  if (organizationId && attributeKey) {
    return inject(SaathratriEntity4Service)
      .find(organizationId, attributeKey)
      .pipe(
        mergeMap((saathratriEntity4: HttpResponse<ISaathratriEntity4>) => {
          if (saathratriEntity4.body) {
            return of(saathratriEntity4.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default saathratriEntity4Resolve;
