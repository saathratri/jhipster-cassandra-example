import { ITag, NewTag } from './tag.model';

export const sampleWithRequiredData: ITag = {
  id: 'sample-id-1',
};

export const sampleWithPartialData: ITag = {
  id: 'sample-id-2',
  name: 'sample-name-2',
};

export const sampleWithFullData: ITag = {
  id: 'sample-id-3',
  name: 'sample-name-3',
  description: 'sample-description-3',
};

export const sampleWithNewData: NewTag = {
  id: 'sample-id-4',
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
