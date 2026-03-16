import { ISaathratriEntity3, NewSaathratriEntity3 } from './saathratri-entity-3.model';

export const sampleWithRequiredData: ISaathratriEntity3 = {
  entityType: 'f93614b0-6a8e-4b38-9738-6fdb8873f739',
};

export const sampleWithPartialData: ISaathratriEntity3 = {
  entityType: 'cbad1b6c-5648-480d-8480-9001d31676db',
  entityName: 'musty geez whoever',
  entityDescription: 'whether which',
  departureDate: 18659,
  tags: 'gadzooks especially meaningfully',
};

export const sampleWithFullData: ISaathratriEntity3 = {
  entityType: 'f8cdf7f0-b68e-4a60-8d6e-08bd3aecf846',
  createdTimeId: '811a0692-91cd-4dee-a709-a6fba2c142cd',
  entityName: 'supplier absentmindedly yellowish',
  entityDescription: 'duh',
  entityCost: 8008.05,
  departureDate: 21117,
  tags: 'agile coolly tricky',
};

export const sampleWithNewData: NewSaathratriEntity3 = {
  entityType: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
