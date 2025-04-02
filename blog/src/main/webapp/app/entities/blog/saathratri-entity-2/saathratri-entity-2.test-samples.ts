import { ISaathratriEntity2, NewSaathratriEntity2 } from './saathratri-entity-2.model';

export const sampleWithRequiredData: ISaathratriEntity2 = {
  entityTypeId: 'a2f983fe-b4e0-49e0-9026-e41a14776d43',
};

export const sampleWithPartialData: ISaathratriEntity2 = {
  entityTypeId: '495910e0-fec0-4f09-ac7d-3d00f22ead14',
  yearOfDateAdded: 30644,
  blogId: '6c70dc92-b16f-4f40-92e6-34b95d34ac53',
  entityName: 'blissfully yet lively',
  entityDescription: 'of bah',
  departureDate: 3300,
};

export const sampleWithFullData: ISaathratriEntity2 = {
  entityTypeId: '7dce17f8-0c66-4d3c-911e-4c390fad83f8',
  yearOfDateAdded: 2718,
  arrivalDate: 14070,
  blogId: '4f95e7bb-5cdf-4ec8-a325-6539f9630446',
  entityName: 'juggernaut once including',
  entityDescription: 'and',
  entityCost: 26860.92,
  departureDate: 1315,
};

export const sampleWithNewData: NewSaathratriEntity2 = {
  entityTypeId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
