/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import { ISaathratriEntity4, NewSaathratriEntity4 } from './saathratri-entity-4.model';

export const sampleWithRequiredData: ISaathratriEntity4 = {
  compositeId: { organizationId: 'sample-organizationId-1', attributeKey: 'sample-attributeKey-1' },
};

export const sampleWithPartialData: ISaathratriEntity4 = {
  compositeId: { organizationId: 'sample-organizationId-2', attributeKey: 'sample-attributeKey-2' },
  attributeValue: 'sample-attributeValue-2',
};

export const sampleWithFullData: ISaathratriEntity4 = {
  compositeId: { organizationId: 'sample-organizationId-3', attributeKey: 'sample-attributeKey-3' },
  attributeValue: 'sample-attributeValue-3',
};

export const sampleWithNewData: NewSaathratriEntity4 = {
  compositeId: { organizationId: 'sample-organizationId-4', attributeKey: 'sample-attributeKey-4' },
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
