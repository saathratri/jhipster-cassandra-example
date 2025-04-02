import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import SaathratriEntity4Resolve from './route/saathratri-entity-4-routing-resolve.service';

const saathratriEntity4Route: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/saathratri-entity-4.component').then(m => m.SaathratriEntity4Component),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:attributeKey/view',
    loadComponent: () => import('./detail/saathratri-entity-4-detail.component').then(m => m.SaathratriEntity4DetailComponent),
    resolve: {
      saathratriEntity4: SaathratriEntity4Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/saathratri-entity-4-update.component').then(m => m.SaathratriEntity4UpdateComponent),
    resolve: {
      saathratriEntity4: SaathratriEntity4Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:attributeKey/edit',
    loadComponent: () => import('./update/saathratri-entity-4-update.component').then(m => m.SaathratriEntity4UpdateComponent),
    resolve: {
      saathratriEntity4: SaathratriEntity4Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default saathratriEntity4Route;
