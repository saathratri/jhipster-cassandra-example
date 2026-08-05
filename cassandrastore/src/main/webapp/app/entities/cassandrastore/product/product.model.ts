/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import dayjs from 'dayjs/esm';

export interface IProduct {
  id: string;
  title?: string | null;
  price?: number | null;
  image?: string | null;
  imageContentType?: string | null;
  addedDate?: dayjs.Dayjs | null;
}

export type NewProduct = Omit<IProduct, 'id'> & { id: string };
