import { ILandingPageByOrganization, NewLandingPageByOrganization } from './landing-page-by-organization.model';

export const sampleWithRequiredData: ILandingPageByOrganization = {
  organizationId: '5e564975-7cb3-47d0-8cb8-93f6c483dde2',
};

export const sampleWithPartialData: ILandingPageByOrganization = {
  organizationId: '848087a3-4c13-459d-9248-fe9adc5f8c7d',
  detailsBigInt: 28861,
};

export const sampleWithFullData: ILandingPageByOrganization = {
  organizationId: 'aad468e6-a330-47e4-ac64-a637b007b829',
  detailsText: 'catch repentant offensively',
  detailsDecimal: 22859.05,
  detailsBoolean: true,
  detailsBigInt: 29098,
};

export const sampleWithNewData: NewLandingPageByOrganization = {
  organizationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
