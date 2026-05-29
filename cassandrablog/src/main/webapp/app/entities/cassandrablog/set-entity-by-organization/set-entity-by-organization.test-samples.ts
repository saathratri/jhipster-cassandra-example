import { ISetEntityByOrganization, NewSetEntityByOrganization } from './set-entity-by-organization.model';

export const sampleWithRequiredData: ISetEntityByOrganization = {
  organizationId: 'sample-organizationId-1',
};

export const sampleWithPartialData: ISetEntityByOrganization = {
  organizationId: 'sample-organizationId-2',
  tags: new Set(['sample-2']),
};

export const sampleWithFullData: ISetEntityByOrganization = {
  organizationId: 'sample-organizationId-3',
  tags: new Set(['sample-3']),
};

export const sampleWithNewData: NewSetEntityByOrganization = {
  organizationId: 'sample-organizationId-4',
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
