import { IAddOnsSelectedByOrganization, NewAddOnsSelectedByOrganization } from './add-ons-selected-by-organization.model';

export const sampleWithRequiredData: IAddOnsSelectedByOrganization = {
  organizationId: 'ba939e9a-9477-43c1-b6d8-d3e17aafc279',
};

export const sampleWithPartialData: IAddOnsSelectedByOrganization = {
  organizationId: '15c16cf6-c42a-43b7-bc95-6d49d57ad12a',
  createdTimeId: '51b8dad7-2378-4c45-a60d-8e6600df3c5f',
  departureDate: 11720,
  customerLastName: 'mmm ick ah',
  customerUpdatedEmail: 'ick ethyl alongside',
  customerUpdatedPhoneNumber: 'unless which wherever',
  customerEstimatedArrivalTime: 'makeover till intensely',
  addOnDetailsText: 'in less',
  addOnDetailsBigInt: 20789,
};

export const sampleWithFullData: IAddOnsSelectedByOrganization = {
  organizationId: 'f7abdb9a-f638-47a9-8cad-1062ebe0b817',
  arrivalDate: 7377,
  accountNumber: 'vague',
  createdTimeId: '4de1cd8f-1ae1-42b0-b36d-ef770134c749',
  departureDate: 18181,
  customerId: '2414fb94-dd22-4856-b2ee-24d4690a44d9',
  customerFirstName: 'esteemed verbally',
  customerLastName: 'happily better',
  customerUpdatedEmail: 'unfortunate archive relative',
  customerUpdatedPhoneNumber: 'aw hence edge',
  customerEstimatedArrivalTime: 'linseed important so',
  tinyUrlShortCode: 'writ horn',
  addOnDetailsText: 'keenly internalise whose',
  addOnDetailsDecimal: 15106.59,
  addOnDetailsBoolean: false,
  addOnDetailsBigInt: 24064,
};

export const sampleWithNewData: NewAddOnsSelectedByOrganization = {
  organizationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
