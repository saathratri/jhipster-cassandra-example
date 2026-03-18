import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'blog',
    data: { pageTitle: 'cassandrablogApp.cassandrablogBlog.home.title' },
    loadChildren: () => import('./cassandrablog/blog/blog.routes'),
  },
  {
    path: 'post',
    data: { pageTitle: 'cassandrablogApp.cassandrablogPost.home.title' },
    loadChildren: () => import('./cassandrablog/post/post.routes'),
  },
  {
    path: 'tag',
    data: { pageTitle: 'cassandrablogApp.cassandrablogTag.home.title' },
    loadChildren: () => import('./cassandrablog/tag/tag.routes'),
  },
  {
    path: 'taj-user',
    data: { pageTitle: 'cassandrablogApp.cassandrablogTajUser.home.title' },
    loadChildren: () => import('./cassandrablog/taj-user/taj-user.routes'),
  },
  {
    path: 'saathratri-entity',
    data: { pageTitle: 'cassandrablogApp.cassandrablogSaathratriEntity.home.title' },
    loadChildren: () => import('./cassandrablog/saathratri-entity/saathratri-entity.routes'),
  },
  {
    path: 'saathratri-entity-2',
    data: { pageTitle: 'cassandrablogApp.cassandrablogSaathratriEntity2.home.title' },
    loadChildren: () => import('./cassandrablog/saathratri-entity-2/saathratri-entity-2.routes'),
  },
  {
    path: 'saathratri-entity-3',
    data: { pageTitle: 'cassandrablogApp.cassandrablogSaathratriEntity3.home.title' },
    loadChildren: () => import('./cassandrablog/saathratri-entity-3/saathratri-entity-3.routes'),
  },
  {
    path: 'saathratri-entity-4',
    data: { pageTitle: 'cassandrablogApp.cassandrablogSaathratriEntity4.home.title' },
    loadChildren: () => import('./cassandrablog/saathratri-entity-4/saathratri-entity-4.routes'),
  },
  {
    path: 'add-ons-available-by-organization',
    data: { pageTitle: 'cassandrablogApp.cassandrablogAddOnsAvailableByOrganization.home.title' },
    loadChildren: () => import('./cassandrablog/add-ons-available-by-organization/add-ons-available-by-organization.routes'),
  },
  {
    path: 'add-ons-selected-by-organization',
    data: { pageTitle: 'cassandrablogApp.cassandrablogAddOnsSelectedByOrganization.home.title' },
    loadChildren: () => import('./cassandrablog/add-ons-selected-by-organization/add-ons-selected-by-organization.routes'),
  },
  {
    path: 'landing-page-by-organization',
    data: { pageTitle: 'cassandrablogApp.cassandrablogLandingPageByOrganization.home.title' },
    loadChildren: () => import('./cassandrablog/landing-page-by-organization/landing-page-by-organization.routes'),
  },
  {
    path: 'set-entity-by-organization',
    data: { pageTitle: 'cassandrablogApp.cassandrablogSetEntityByOrganization.home.title' },
    loadChildren: () => import('./cassandrablog/set-entity-by-organization/set-entity-by-organization.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
