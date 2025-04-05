import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'blog',
    data: { pageTitle: 'blogApp.blogBlog.home.title' },
    loadChildren: () => import('./blog/blog/blog.routes'),
  },
  {
    path: 'post',
    data: { pageTitle: 'blogApp.blogPost.home.title' },
    loadChildren: () => import('./blog/post/post.routes'),
  },
  {
    path: 'tag',
    data: { pageTitle: 'blogApp.blogTag.home.title' },
    loadChildren: () => import('./blog/tag/tag.routes'),
  },
  {
    path: 'taj-user',
    data: { pageTitle: 'blogApp.blogTajUser.home.title' },
    loadChildren: () => import('./blog/taj-user/taj-user.routes'),
  },
  {
    path: 'saathratri-entity',
    data: { pageTitle: 'blogApp.blogSaathratriEntity.home.title' },
    loadChildren: () => import('./blog/saathratri-entity/saathratri-entity.routes'),
  },
  {
    path: 'saathratri-entity-2',
    data: { pageTitle: 'blogApp.blogSaathratriEntity2.home.title' },
    loadChildren: () => import('./blog/saathratri-entity-2/saathratri-entity-2.routes'),
  },
  {
    path: 'saathratri-entity-3',
    data: { pageTitle: 'blogApp.blogSaathratriEntity3.home.title' },
    loadChildren: () => import('./blog/saathratri-entity-3/saathratri-entity-3.routes'),
  },
  {
    path: 'saathratri-entity-4',
    data: { pageTitle: 'blogApp.blogSaathratriEntity4.home.title' },
    loadChildren: () => import('./blog/saathratri-entity-4/saathratri-entity-4.routes'),
  },
  {
    path: 'add-ons-available-by-organization',
    data: { pageTitle: 'blogApp.blogAddOnsAvailableByOrganization.home.title' },
    loadChildren: () => import('./blog/add-ons-available-by-organization/add-ons-available-by-organization.routes'),
  },
  {
    path: 'add-ons-selected-by-organization',
    data: { pageTitle: 'blogApp.blogAddOnsSelectedByOrganization.home.title' },
    loadChildren: () => import('./blog/add-ons-selected-by-organization/add-ons-selected-by-organization.routes'),
  },
  {
    path: 'landing-page-by-organization',
    data: { pageTitle: 'blogApp.blogLandingPageByOrganization.home.title' },
    loadChildren: () => import('./blog/landing-page-by-organization/landing-page-by-organization.routes'),
  },
  {
    path: 'set-entity-by-organization',
    data: { pageTitle: 'blogApp.blogSetEntityByOrganization.home.title' },
    loadChildren: () => import('./blog/set-entity-by-organization/set-entity-by-organization.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
