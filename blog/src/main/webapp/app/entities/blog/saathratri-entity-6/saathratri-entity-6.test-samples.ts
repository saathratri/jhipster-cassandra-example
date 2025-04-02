import { ISaathratriEntity6, NewSaathratriEntity6 } from './saathratri-entity-6.model';

export const sampleWithRequiredData: ISaathratriEntity6 = {
  organizationId: '6cb191e4-99d0-4f83-8000-7c03312e012c',
};

export const sampleWithPartialData: ISaathratriEntity6 = {
  organizationId: '17bb2840-40a4-4bff-b40b-40f2b875a0a2',
  accountNumber: 'ick trivial because',
  createdTimeId: '60e3cf75-63c3-4b4f-ae3a-85a78a37e943',
  departureDate: 1417,
  customerUpdatedPhoneNumber: 'zowie log oof',
  customerEstimatedArrivalTime: 'incidentally or blah',
  addOnDetailsText: 'impossible maintainer fibre',
  addOnDetailsDecimal: 23323.64,
};

export const sampleWithFullData: ISaathratriEntity6 = {
  organizationId: '4e051840-92bc-4d5b-9154-cb5333b98bf1',
  arrivalDate: 30851,
  accountNumber: 'variable',
  createdTimeId: 'c1176c09-b596-40ce-8566-51869ac0cb30',
  departureDate: 260,
  customerId: '7a9fe2df-1724-437f-b769-eb22459848f2',
  customerFirstName: 'ouch',
  customerLastName: 'yippee',
  customerUpdatedEmail: 'shinny',
  customerUpdatedPhoneNumber: 'boohoo',
  customerEstimatedArrivalTime: 'ha',
  tinyUrlShortCode: 'lively rarely',
  addOnDetailsText: 'afterwards',
  addOnDetailsDecimal: 31184.57,
  addOnDetailsBoolean: false,
  addOnDetailsBigInt: 17054,
};

export const sampleWithNewData: NewSaathratriEntity6 = {
  organizationId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
