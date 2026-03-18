import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

import BlogResolve from './route/blog-routing-resolve.service';

const blogRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/blog').then(m => m.BlogComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':category/:blogId/view',
    loadComponent: () => import('./detail/blog-detail').then(m => m.BlogDetailComponent),
    resolve: {
      blog: BlogResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/blog-update').then(m => m.BlogUpdateComponent),
    resolve: {
      blog: BlogResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':category/:blogId/edit',
    loadComponent: () => import('./update/blog-update').then(m => m.BlogUpdateComponent),
    resolve: {
      blog: BlogResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default blogRoute;
