import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import AddOnsAvailableByOrganizationResolve from './route/add-ons-available-by-organization-routing-resolve.service';

const addOnsAvailableByOrganizationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/add-ons-available-by-organization.component').then(m => m.AddOnsAvailableByOrganizationComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:entityType/:entityId/:addOnId/view',
    loadComponent: () =>
      import('./detail/add-ons-available-by-organization-detail.component').then(m => m.AddOnsAvailableByOrganizationDetailComponent),
    resolve: {
      addOnsAvailableByOrganization: AddOnsAvailableByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/add-ons-available-by-organization-update.component').then(m => m.AddOnsAvailableByOrganizationUpdateComponent),
    resolve: {
      addOnsAvailableByOrganization: AddOnsAvailableByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:entityType/:entityId/:addOnId/edit',
    loadComponent: () =>
      import('./update/add-ons-available-by-organization-update.component').then(m => m.AddOnsAvailableByOrganizationUpdateComponent),
    resolve: {
      addOnsAvailableByOrganization: AddOnsAvailableByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default addOnsAvailableByOrganizationRoute;
