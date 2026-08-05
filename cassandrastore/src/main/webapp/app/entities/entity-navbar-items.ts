/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import NavbarItem from 'app/layouts/navbar/navbar-item.model';

export const EntityNavbarItems: NavbarItem[] = [
  {
    name: 'Product',
    route: '/cassandrastore/product',
    translationKey: 'global.menu.entities.cassandrastoreProduct',
  },
  {
    name: 'Report',
    route: '/cassandrastore/report',
    translationKey: 'global.menu.entities.cassandrastoreReport',
  },
  /* jhipster-needle-add-entity-navbar - JHipster will add entity navbar items here */
];
