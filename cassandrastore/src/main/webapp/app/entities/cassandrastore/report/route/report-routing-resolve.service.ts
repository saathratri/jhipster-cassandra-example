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

import { IReport } from '../report.model';
import { ReportService } from '../service/report.service';

const reportResolve = (route: ActivatedRouteSnapshot): Observable<null | IReport> => {
  const { id } = route.params;

  if (id) {
    return inject(ReportService)
      .find(id)
      .pipe(
        mergeMap((report: HttpResponse<IReport>) => {
          if (report.body) {
            return of(report.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default reportResolve;
