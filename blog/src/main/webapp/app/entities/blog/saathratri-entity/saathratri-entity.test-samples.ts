import { ISaathratriEntity, NewSaathratriEntity } from './saathratri-entity.model';

export const sampleWithRequiredData: ISaathratriEntity = {
  entityId: 'fcb7dfb2-1dfe-4366-b58f-77d3cbc0715f',
};

export const sampleWithPartialData: ISaathratriEntity = {
  entityId: '674ba0f0-1ea0-4c29-9b02-2da807b9c246',
  entityDescription: 'known bouncy',
  createdTimeId: '20697717-1090-464e-ba0c-fb68bb49d4bd',
};

export const sampleWithFullData: ISaathratriEntity = {
  entityId: 'e9e5b4be-139c-4738-beb5-d9962181a751',
  entityName: 'pish',
  entityDescription: 'marimba astride',
  entityCost: 24301.8,
  createdId: '71ec48f5-f4c8-4b89-af5d-40b1456309c9',
  createdTimeId: 'a49facd6-4a9d-4a59-88f4-12c89da87e3a',
};

export const sampleWithNewData: NewSaathratriEntity = {
  entityId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
