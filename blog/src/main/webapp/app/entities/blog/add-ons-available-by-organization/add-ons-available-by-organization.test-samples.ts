import { IAddOnsAvailableByOrganization, NewAddOnsAvailableByOrganization } from './add-ons-available-by-organization.model';

export const sampleWithRequiredData: IAddOnsAvailableByOrganization = {
  organizationId: 'aef962cf-f8f9-482d-aa2a-7b2c9d20cc57',
};

export const sampleWithPartialData: IAddOnsAvailableByOrganization = {
  organizationId: '048427b0-9f2a-4360-bf35-ade270f91499',
  entityType: 'overconfidently defiantly boohoo',
  entityId: 'd3198528-35ca-4a0f-83dd-f648c63cf039',
  addOnDetailsText: 'geez',
};

export const sampleWithFullData: IAddOnsAvailableByOrganization = {
  organizationId: '863567cc-20fe-48aa-8860-31a928d4fa9e',
  entityType: 'into hygienic cripple',
  entityId: '21709217-05ba-4260-9a37-fd341329acb8',
  addOnId: '35921b1c-0301-4db5-9ae1-46e2b5c76335',
  addOnType: 'gosh ack',
  addOnDetailsText: 'light affiliate ack',
  addOnDetailsDecimal: 5729.17,
  addOnDetailsBoolean: false,
  addOnDetailsBigInt: 12102,
};

export const sampleWithNewData: NewAddOnsAvailableByOrganization = {
  organizationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
