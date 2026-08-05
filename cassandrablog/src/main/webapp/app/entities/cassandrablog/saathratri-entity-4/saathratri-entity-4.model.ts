/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

export interface ISaathratriEntity4 {
  compositeId: ISaathratriEntity4Id;
  attributeValue?: string | null;
}
export interface ISaathratriEntity4Id {
  organizationId: string | null;
  attributeKey: string | null;
}

export type NewSaathratriEntity4 = Omit<ISaathratriEntity4, 'compositeId'> & { compositeId: ISaathratriEntity4Id };
