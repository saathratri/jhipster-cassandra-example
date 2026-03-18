import { ISetEntityByOrganization, NewSetEntityByOrganization } from './set-entity-by-organization.model';

export const sampleWithRequiredData: ISetEntityByOrganization = {
  organizationId: '447830be-82a7-429d-8aeb-a8924089ff42',
};

export const sampleWithPartialData: ISetEntityByOrganization = {
  organizationId: 'a28bb74a-acee-4f0e-93ef-8a902fc02e09',
};

export const sampleWithFullData: ISetEntityByOrganization = {
  organizationId: 'bea9a0b8-c676-4258-b9ba-d753a9775ca2',
  tags: 'for',
};

export const sampleWithNewData: NewSetEntityByOrganization = {
  organizationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
