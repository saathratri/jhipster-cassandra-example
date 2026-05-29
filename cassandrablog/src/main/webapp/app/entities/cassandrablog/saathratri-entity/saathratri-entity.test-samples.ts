import { ISaathratriEntity, NewSaathratriEntity } from './saathratri-entity.model';

export const sampleWithRequiredData: ISaathratriEntity = {
  entityId: 'sample-entityId-1',
};

export const sampleWithPartialData: ISaathratriEntity = {
  entityId: 'sample-entityId-2',
  entityName: 'sample-entityName-2',
  entityDescription: 'sample-entityDescription-2',
  entityCost: 1002,
};

export const sampleWithFullData: ISaathratriEntity = {
  entityId: 'sample-entityId-3',
  entityName: 'sample-entityName-3',
  entityDescription: 'sample-entityDescription-3',
  entityCost: 1003,
  createdId: 'sample-createdId-3',
  createdTimeId: 'sample-createdTimeId-3',
};

export const sampleWithNewData: NewSaathratriEntity = {
  entityId: 'sample-entityId-4',
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
