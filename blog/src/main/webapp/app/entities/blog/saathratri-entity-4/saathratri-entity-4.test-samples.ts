import { ISaathratriEntity4, NewSaathratriEntity4 } from './saathratri-entity-4.model';

export const sampleWithRequiredData: ISaathratriEntity4 = {
  organizationId: 'acfa3502-9b1d-4af1-9821-e9fc560aed23',
};

export const sampleWithPartialData: ISaathratriEntity4 = {
  organizationId: '14cefa85-8aac-4d69-ad06-f728a3ebd8d5',
  attributeKey: 'till gut comparison',
};

export const sampleWithFullData: ISaathratriEntity4 = {
  organizationId: '64c88438-f57c-457f-bf6a-4b2d2fe80991',
  attributeKey: 'twist down',
  attributeValue: 'as till construe',
};

export const sampleWithNewData: NewSaathratriEntity4 = {
  organizationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
