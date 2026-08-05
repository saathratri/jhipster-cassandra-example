/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import dayjs from 'dayjs/esm';

export interface ILandingPageByOrganization {
  organizationId: string;
  detailsText?: Record<string, string> | null;
  detailsDecimal?: Record<string, number> | null;
  detailsBoolean?: Record<string, boolean> | null;
  detailsBigInt?: Record<string, dayjs.Dayjs> | null;
}

export type NewLandingPageByOrganization = Omit<ILandingPageByOrganization, 'organizationId'> & { organizationId: string };
