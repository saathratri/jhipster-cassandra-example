import { IAddOnsAvailableByOrganization, NewAddOnsAvailableByOrganization } from './add-ons-available-by-organization.model';

export const sampleWithRequiredData: IAddOnsAvailableByOrganization = {
  organizationId: '68b6a211-8b03-44a6-8c88-0415f39b5133',
};

export const sampleWithPartialData: IAddOnsAvailableByOrganization = {
  organizationId: 'ab3e8ef2-6417-4f9f-830a-2228cc288e79',
  entityId: '5c1c77b8-9d20-4ad1-8f02-0121c4fbe0d1',
  addOnId: 'cb1705b5-a1a6-4427-8370-0cf599307360',
  addOnType: 'whether unrealistic',
  addOnDetailsText: 'ruin',
  addOnDetailsBoolean: true,
};

export const sampleWithFullData: IAddOnsAvailableByOrganization = {
  organizationId: 'cd8392a0-5be2-4ecf-9bcb-3e3f5345eb3c',
  entityType: 'disrespect',
  entityId: '04c3e8ec-03aa-44ad-ba9b-a20ba4d03157',
  addOnId: 'c7788238-50ea-4c2b-a2f6-2a8d8993a906',
  addOnType: 'anti phew',
  addOnDetailsText: 'joyously mid',
  addOnDetailsDecimal: 22380.73,
  addOnDetailsBoolean: true,
  addOnDetailsBigInt: 17811,
};

export const sampleWithNewData: NewAddOnsAvailableByOrganization = {
  organizationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
