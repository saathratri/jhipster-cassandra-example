/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import { HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { TajUserService } from '../service/taj-user.service';
import { ITajUser } from '../taj-user.model';

const tajUserResolve = (route: ActivatedRouteSnapshot): Observable<null | ITajUser> => {
  const { id } = route.params;

  if (id) {
    return inject(TajUserService)
      .find(id)
      .pipe(
        mergeMap((tajUser: HttpResponse<ITajUser>) => {
          if (tajUser.body) {
            return of(tajUser.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default tajUserResolve;
