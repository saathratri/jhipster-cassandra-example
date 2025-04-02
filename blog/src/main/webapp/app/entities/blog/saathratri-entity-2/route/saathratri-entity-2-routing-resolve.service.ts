import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISaathratriEntity2 } from '../saathratri-entity-2.model';
import { SaathratriEntity2Service } from '../service/saathratri-entity-2.service';

const saathratriEntity2Resolve = (route: ActivatedRouteSnapshot): Observable<null | ISaathratriEntity2> => {
  const entityTypeId = route.params.entityTypeId;
  const yearOfDateAdded = route.params.yearOfDateAdded;
  const arrivalDate = route.params.arrivalDate;
  const blogId = route.params.blogId;

  if (entityTypeId && yearOfDateAdded && arrivalDate && blogId) {
    return inject(SaathratriEntity2Service)
      .find(entityTypeId, yearOfDateAdded, arrivalDate, blogId)
      .pipe(
        mergeMap((saathratriEntity2: HttpResponse<ISaathratriEntity2>) => {
          if (saathratriEntity2.body) {
            return of(saathratriEntity2.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default saathratriEntity2Resolve;
