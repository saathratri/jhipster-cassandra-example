import { ILandingPageByOrganization, NewLandingPageByOrganization } from './landing-page-by-organization.model';

export const sampleWithRequiredData: ILandingPageByOrganization = {
  organizationId: 'edd1e483-8b17-48c0-b8b2-b31ec8d9b4f2',
};

export const sampleWithPartialData: ILandingPageByOrganization = {
  organizationId: 'd929d523-b01b-4219-b1d7-accf53caa37f',
  detailsText: 'provider till',
  detailsDecimal: 2926.34,
  detailsBigInt: 14670,
};

export const sampleWithFullData: ILandingPageByOrganization = {
  organizationId: '188d714d-a9a0-448a-b6ba-3fed40feedb0',
  detailsText: 'unless masculinize so',
  detailsDecimal: 18372.01,
  detailsBoolean: false,
  detailsBigInt: 11132,
};

export const sampleWithNewData: NewLandingPageByOrganization = {
  organizationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
