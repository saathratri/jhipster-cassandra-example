/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

export interface ISetEntityByOrganization {
  organizationId: string;
  tags?: Set<string> | null;
}

export type NewSetEntityByOrganization = Omit<ISetEntityByOrganization, 'organizationId'> & { organizationId: string };
