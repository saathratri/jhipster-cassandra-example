import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import SaathratriEntity5Resolve from './route/saathratri-entity-5-routing-resolve.service';

const saathratriEntity5Route: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/saathratri-entity-5.component').then(m => m.SaathratriEntity5Component),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:entityType/:entityId/:addOnId/view',
    loadComponent: () => import('./detail/saathratri-entity-5-detail.component').then(m => m.SaathratriEntity5DetailComponent),
    resolve: {
      saathratriEntity5: SaathratriEntity5Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/saathratri-entity-5-update.component').then(m => m.SaathratriEntity5UpdateComponent),
    resolve: {
      saathratriEntity5: SaathratriEntity5Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:entityType/:entityId/:addOnId/edit',
    loadComponent: () => import('./update/saathratri-entity-5-update.component').then(m => m.SaathratriEntity5UpdateComponent),
    resolve: {
      saathratriEntity5: SaathratriEntity5Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default saathratriEntity5Route;
