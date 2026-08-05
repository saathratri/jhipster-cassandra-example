/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import SaathratriEntity3Resolve from './route/saathratri-entity-3-routing-resolve.service';

const saathratriEntity3Route: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/saathratri-entity-3').then(m => m.SaathratriEntity3Component),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityType/:createdTimeId/view',
    loadComponent: () => import('./detail/saathratri-entity-3-detail').then(m => m.SaathratriEntity3DetailComponent),
    resolve: {
      saathratriEntity3: SaathratriEntity3Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/saathratri-entity-3-update').then(m => m.SaathratriEntity3UpdateComponent),
    resolve: {
      saathratriEntity3: SaathratriEntity3Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityType/:createdTimeId/edit',
    loadComponent: () => import('./update/saathratri-entity-3-update').then(m => m.SaathratriEntity3UpdateComponent),
    resolve: {
      saathratriEntity3: SaathratriEntity3Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default saathratriEntity3Route;
