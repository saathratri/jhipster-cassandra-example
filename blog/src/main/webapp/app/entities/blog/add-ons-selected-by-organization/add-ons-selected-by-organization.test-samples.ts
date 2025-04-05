import { IAddOnsSelectedByOrganization, NewAddOnsSelectedByOrganization } from './add-ons-selected-by-organization.model';

export const sampleWithRequiredData: IAddOnsSelectedByOrganization = {
  organizationId: 'aaec16f8-cc89-4f66-8381-efc2b3edf727',
};

export const sampleWithPartialData: IAddOnsSelectedByOrganization = {
  organizationId: '5d054211-15c4-4957-9999-844a2d2b9479',
  accountNumber: 'because via',
  customerId: '6a9981ae-34de-42b7-b458-7dfc2a5d10c5',
  customerFirstName: 'closely',
  customerLastName: 'fatal technician',
  customerUpdatedEmail: 'kiss overcook',
  tinyUrlShortCode: 'fooey wonderfully except',
  addOnDetailsText: 'exalted',
  addOnDetailsBoolean: true,
  addOnDetailsBigInt: 31234,
};

export const sampleWithFullData: IAddOnsSelectedByOrganization = {
  organizationId: '24e868b6-00fe-42e9-9fa3-a604c5e24a42',
  arrivalDate: 25582,
  accountNumber: 'pish consequently',
  createdTimeId: '750d8f8c-e88a-4139-8f37-0e2c5c7b794e',
  departureDate: 179,
  customerId: '6a8837f8-8107-48b6-b058-9a08cb45e489',
  customerFirstName: 'tasty',
  customerLastName: 'immediately ah',
  customerUpdatedEmail: 'among',
  customerUpdatedPhoneNumber: 'personalise',
  customerEstimatedArrivalTime: 'lest freight',
  tinyUrlShortCode: 'utilization on gosh',
  addOnDetailsText: 'on moment',
  addOnDetailsDecimal: 24997.63,
  addOnDetailsBoolean: false,
  addOnDetailsBigInt: 2333,
};

export const sampleWithNewData: NewAddOnsSelectedByOrganization = {
  organizationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
