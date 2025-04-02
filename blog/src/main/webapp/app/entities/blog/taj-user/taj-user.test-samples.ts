import { ITajUser, NewTajUser } from './taj-user.model';

export const sampleWithRequiredData: ITajUser = {
  id: '1435fcf7-d1c3-463d-b6e8-ad38de60bd9f',
  login: 'notwithstanding junior plus',
};

export const sampleWithPartialData: ITajUser = {
  id: 'f2fd7f80-0218-4ba0-a482-93cf9395fcd5',
  login: 'burly idealistic yearn',
};

export const sampleWithFullData: ITajUser = {
  id: '715f3f5f-f9e8-4438-8f13-828ce5363f03',
  login: 'midst scientific',
};

export const sampleWithNewData: NewTajUser = {
  login: 'switchboard thoughtfully',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
