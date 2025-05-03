import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import SaathratriEntityResolve from './route/saathratri-entity-routing-resolve.service';

const saathratriEntityRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/saathratri-entity.component').then(m => m.SaathratriEntityComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityId/view',
    loadComponent: () => import('./detail/saathratri-entity-detail.component').then(m => m.SaathratriEntityDetailComponent),
    resolve: {
      saathratriEntity: SaathratriEntityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/saathratri-entity-update.component').then(m => m.SaathratriEntityUpdateComponent),
    resolve: {
      saathratriEntity: SaathratriEntityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':entityId/edit',
    loadComponent: () => import('./update/saathratri-entity-update.component').then(m => m.SaathratriEntityUpdateComponent),
    resolve: {
      saathratriEntity: SaathratriEntityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default saathratriEntityRoute;
