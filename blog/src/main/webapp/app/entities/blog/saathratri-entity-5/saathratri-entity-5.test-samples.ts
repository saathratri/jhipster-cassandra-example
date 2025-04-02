import { ISaathratriEntity5, NewSaathratriEntity5 } from './saathratri-entity-5.model';

export const sampleWithRequiredData: ISaathratriEntity5 = {
  organizationId: '9b0770be-f6bd-48c0-8b84-60afaab00812',
};

export const sampleWithPartialData: ISaathratriEntity5 = {
  organizationId: '60d39fc7-9500-4e3e-81f9-5b6ad20f1983',
  addOnId: 'fdecd902-44e8-43ee-ad9e-d38c4b5ccda8',
};

export const sampleWithFullData: ISaathratriEntity5 = {
  organizationId: '67fed806-1115-44d5-a279-8e7df0457d10',
  entityType: 'tightly hm for',
  entityId: '63ec1ea3-3024-458f-9649-bc6773119441',
  addOnId: '40009280-d683-4f9c-aeb6-5ea19f593f4c',
  addOnType: 'stupendous evil yippee',
  addOnDetailsText: 'until whereas',
  addOnDetailsDecimal: 22593.09,
  addOnDetailsBoolean: false,
  addOnDetailsBigInt: 29310,
};

export const sampleWithNewData: NewSaathratriEntity5 = {
  organizationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
