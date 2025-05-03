import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import AddOnsSelectedByOrganizationResolve from './route/add-ons-selected-by-organization-routing-resolve.service';

const addOnsSelectedByOrganizationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/add-ons-selected-by-organization.component').then(m => m.AddOnsSelectedByOrganizationComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:arrivalDate/:accountNumber/:createdTimeId/view',
    loadComponent: () =>
      import('./detail/add-ons-selected-by-organization-detail.component').then(m => m.AddOnsSelectedByOrganizationDetailComponent),
    resolve: {
      addOnsSelectedByOrganization: AddOnsSelectedByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/add-ons-selected-by-organization-update.component').then(m => m.AddOnsSelectedByOrganizationUpdateComponent),
    resolve: {
      addOnsSelectedByOrganization: AddOnsSelectedByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/:arrivalDate/:accountNumber/:createdTimeId/edit',
    loadComponent: () =>
      import('./update/add-ons-selected-by-organization-update.component').then(m => m.AddOnsSelectedByOrganizationUpdateComponent),
    resolve: {
      addOnsSelectedByOrganization: AddOnsSelectedByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default addOnsSelectedByOrganizationRoute;
