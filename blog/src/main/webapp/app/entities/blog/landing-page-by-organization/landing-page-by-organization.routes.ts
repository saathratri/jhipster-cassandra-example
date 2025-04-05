import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import LandingPageByOrganizationResolve from './route/landing-page-by-organization-routing-resolve.service';

const landingPageByOrganizationRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/landing-page-by-organization.component').then(m => m.LandingPageByOrganizationComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/view',
    loadComponent: () =>
      import('./detail/landing-page-by-organization-detail.component').then(m => m.LandingPageByOrganizationDetailComponent),
    resolve: {
      landingPageByOrganization: LandingPageByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () =>
      import('./update/landing-page-by-organization-update.component').then(m => m.LandingPageByOrganizationUpdateComponent),
    resolve: {
      landingPageByOrganization: LandingPageByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':organizationId/edit',
    loadComponent: () =>
      import('./update/landing-page-by-organization-update.component').then(m => m.LandingPageByOrganizationUpdateComponent),
    resolve: {
      landingPageByOrganization: LandingPageByOrganizationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default landingPageByOrganizationRoute;
