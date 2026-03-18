import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'product',
    data: { pageTitle: 'cassandrastoreApp.cassandrastoreProduct.home.title' },
    loadChildren: () => import('./cassandrastore/product/product.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
