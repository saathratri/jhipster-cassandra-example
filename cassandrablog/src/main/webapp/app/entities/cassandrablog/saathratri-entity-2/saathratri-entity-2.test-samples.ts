import { ISaathratriEntity2, NewSaathratriEntity2 } from './saathratri-entity-2.model';

export const sampleWithRequiredData: ISaathratriEntity2 = {
  entityTypeId: '5974f795-b16d-40ae-a874-7c5737f16b14',
};

export const sampleWithPartialData: ISaathratriEntity2 = {
  entityTypeId: '2f2aca45-cce6-4c6d-a2bd-94fc7e479a8d',
  yearOfDateAdded: 29203,
  arrivalDate: 8788,
  entityName: 'luck',
  departureDate: 14782,
};

export const sampleWithFullData: ISaathratriEntity2 = {
  entityTypeId: '5a2ce557-5df7-4a42-b07f-2ee244ac5d29',
  yearOfDateAdded: 20695,
  arrivalDate: 12535,
  blogId: 'ebe19d89-7627-4642-b83a-898c4d86c540',
  entityName: 'haul',
  entityDescription: 'angle whereas hypothesize',
  entityCost: 15059.87,
  departureDate: 9824,
};

export const sampleWithNewData: NewSaathratriEntity2 = {
  entityTypeId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
