import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import SaathratriEntity3Resolve from './route/saathratri-entity-3-routing-resolve.service';

const saathratriEntity3Route: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/saathratri-entity-3.component').then(m => m.SaathratriEntity3Component),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityType/:createdTimeId/view',
    loadComponent: () => import('./detail/saathratri-entity-3-detail.component').then(m => m.SaathratriEntity3DetailComponent),
    resolve: {
      saathratriEntity3: SaathratriEntity3Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/saathratri-entity-3-update.component').then(m => m.SaathratriEntity3UpdateComponent),
    resolve: {
      saathratriEntity3: SaathratriEntity3Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityType/:createdTimeId/edit',
    loadComponent: () => import('./update/saathratri-entity-3-update.component').then(m => m.SaathratriEntity3UpdateComponent),
    resolve: {
      saathratriEntity3: SaathratriEntity3Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default saathratriEntity3Route;
