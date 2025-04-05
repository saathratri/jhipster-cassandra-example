import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import SetEntityByOrganizationResolve from './route/set-entity-by-organization-routing-resolve.service';

const setEntityByOrganizationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/set-entity-by-organization.component').then(m => m.SetEntityByOrganizationComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/view',
    loadComponent: () => import('./detail/set-entity-by-organization-detail.component').then(m => m.SetEntityByOrganizationDetailComponent),
    resolve: {
      setEntityByOrganization: SetEntityByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/set-entity-by-organization-update.component').then(m => m.SetEntityByOrganizationUpdateComponent),
    resolve: {
      setEntityByOrganization: SetEntityByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/edit',
    loadComponent: () => import('./update/set-entity-by-organization-update.component').then(m => m.SetEntityByOrganizationUpdateComponent),
    resolve: {
      setEntityByOrganization: SetEntityByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default setEntityByOrganizationRoute;
