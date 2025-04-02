import { ISaathratriEntity4, NewSaathratriEntity4 } from './saathratri-entity-4.model';

export const sampleWithRequiredData: ISaathratriEntity4 = {
  organizationId: 'bfcd27db-1586-42e9-83cc-a6a5b4768631',
};

export const sampleWithPartialData: ISaathratriEntity4 = {
  organizationId: 'bf8229ce-1eef-46d2-9daa-c2f56c1dcfc4',
};

export const sampleWithFullData: ISaathratriEntity4 = {
  organizationId: '76623454-52dc-4e60-bf64-332cda8a7a8f',
  attributeKey: 'nun',
  attributeValue: 'furthermore',
};

export const sampleWithNewData: NewSaathratriEntity4 = {
  organizationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
