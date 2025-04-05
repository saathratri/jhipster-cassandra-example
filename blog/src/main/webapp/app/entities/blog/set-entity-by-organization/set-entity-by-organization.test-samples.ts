import { ISetEntityByOrganization, NewSetEntityByOrganization } from './set-entity-by-organization.model';

export const sampleWithRequiredData: ISetEntityByOrganization = {
  organizationId: '76e9fba9-727d-4d24-8240-12b4e7a4f169',
};

export const sampleWithPartialData: ISetEntityByOrganization = {
  organizationId: 'bc3a7976-d054-4556-8352-da9af953d6fc',
};

export const sampleWithFullData: ISetEntityByOrganization = {
  organizationId: '028e60c6-4998-4138-9d4c-a9771b3a6ad9',
  tags: 'yum',
};

export const sampleWithNewData: NewSetEntityByOrganization = {
  organizationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
