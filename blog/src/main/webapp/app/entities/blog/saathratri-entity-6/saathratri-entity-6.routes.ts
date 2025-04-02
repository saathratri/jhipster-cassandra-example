import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import SaathratriEntity6Resolve from './route/saathratri-entity-6-routing-resolve.service';

const saathratriEntity6Route: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/saathratri-entity-6.component').then(m => m.SaathratriEntity6Component),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:arrivalDate/:accountNumber/:createdTimeId/view',
    loadComponent: () => import('./detail/saathratri-entity-6-detail.component').then(m => m.SaathratriEntity6DetailComponent),
    resolve: {
      saathratriEntity6: SaathratriEntity6Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/saathratri-entity-6-update.component').then(m => m.SaathratriEntity6UpdateComponent),
    resolve: {
      saathratriEntity6: SaathratriEntity6Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:arrivalDate/:accountNumber/:createdTimeId/edit',
    loadComponent: () => import('./update/saathratri-entity-6-update.component').then(m => m.SaathratriEntity6UpdateComponent),
    resolve: {
      saathratriEntity6: SaathratriEntity6Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default saathratriEntity6Route;
