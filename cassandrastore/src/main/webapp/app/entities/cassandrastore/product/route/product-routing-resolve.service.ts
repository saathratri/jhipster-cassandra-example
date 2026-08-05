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

import { IProduct } from '../product.model';
import { ProductService } from '../service/product.service';

const productResolve = (route: ActivatedRouteSnapshot): Observable<null | IProduct> => {
  const { id } = route.params;

  if (id) {
    return inject(ProductService)
      .find(id)
      .pipe(
        mergeMap((product: HttpResponse<IProduct>) => {
          if (product.body) {
            return of(product.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default productResolve;
