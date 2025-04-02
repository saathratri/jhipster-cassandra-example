import { ISaathratriEntity3, NewSaathratriEntity3 } from './saathratri-entity-3.model';

export const sampleWithRequiredData: ISaathratriEntity3 = {
  entityType: '27c3dd75-d195-4a21-9337-d013125ad7cc',
};

export const sampleWithPartialData: ISaathratriEntity3 = {
  entityType: 'd641a08a-bff2-407c-a111-5a74134671ea',
  entityDescription: 'but abseil physically',
  entityCost: 7057.75,
  tags: 'cuddly though yum',
};

export const sampleWithFullData: ISaathratriEntity3 = {
  entityType: '20538aca-e476-42d1-9030-828f8d947c6b',
  createdTimeId: 'eb53c066-380e-45b6-972b-f8f1dce435b6',
  entityName: 'authorized modulo',
  entityDescription: 'humble until',
  entityCost: 17108.58,
  departureDate: 17168,
  tags: 'because',
};

export const sampleWithNewData: NewSaathratriEntity3 = {
  entityType: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
