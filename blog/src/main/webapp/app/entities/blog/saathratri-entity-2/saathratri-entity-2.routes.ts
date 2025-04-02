import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import SaathratriEntity2Resolve from './route/saathratri-entity-2-routing-resolve.service';

const saathratriEntity2Route: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/saathratri-entity-2.component').then(m => m.SaathratriEntity2Component),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId/view',
    loadComponent: () => import('./detail/saathratri-entity-2-detail.component').then(m => m.SaathratriEntity2DetailComponent),
    resolve: {
      saathratriEntity2: SaathratriEntity2Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/saathratri-entity-2-update.component').then(m => m.SaathratriEntity2UpdateComponent),
    resolve: {
      saathratriEntity2: SaathratriEntity2Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityTypeId/:yearOfDateAdded/:arrivalDate/:blogId/edit',
    loadComponent: () => import('./update/saathratri-entity-2-update.component').then(m => m.SaathratriEntity2UpdateComponent),
    resolve: {
      saathratriEntity2: SaathratriEntity2Resolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default saathratriEntity2Route;
