import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISaathratriEntity } from '../saathratri-entity.model';
import { SaathratriEntityService } from '../service/saathratri-entity.service';

const saathratriEntityResolve = (route: ActivatedRouteSnapshot): Observable<null | ISaathratriEntity> => {
  const id = route.params.entityId;

  if (id) {
    return inject(SaathratriEntityService)
      .find(id)
      .pipe(
        mergeMap((saathratriEntity: HttpResponse<ISaathratriEntity>) => {
          if (saathratriEntity.body) {
            return of(saathratriEntity.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default saathratriEntityResolve;
