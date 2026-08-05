import { ITajUser, NewTajUser } from './taj-user.model';

export const sampleWithRequiredData: ITajUser = {
  id: 'sample-id-1',
};

export const sampleWithPartialData: ITajUser = {
  id: 'sample-id-2',
  login: 'sample-login-2',
};

export const sampleWithFullData: ITajUser = {
  id: 'sample-id-3',
  login: 'sample-login-3',
};

export const sampleWithNewData: NewTajUser = {
  id: 'sample-id-4',
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
