/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import dayjs from 'dayjs/esm';

export interface IAddOnsAvailableByOrganization {
  compositeId: IAddOnsAvailableByOrganizationId;
  addOnType?: string | null;
  addOnDetailsText?: Record<string, string> | null;
  addOnDetailsDecimal?: Record<string, number> | null;
  addOnDetailsBoolean?: Record<string, boolean> | null;
  addOnDetailsBigInt?: Record<string, dayjs.Dayjs> | null;
}
export interface IAddOnsAvailableByOrganizationId {
  organizationId: string | null;
  entityType: string | null;
  entityId: string | null;
  addOnId: string | null;
}

export type NewAddOnsAvailableByOrganization = Omit<IAddOnsAvailableByOrganization, 'compositeId'> & {
  compositeId: IAddOnsAvailableByOrganizationId;
};
