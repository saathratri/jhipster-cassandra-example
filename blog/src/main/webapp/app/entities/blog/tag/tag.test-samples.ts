import { ITag, NewTag } from './tag.model';

export const sampleWithRequiredData: ITag = {
  id: 'ac74efac-9c74-473e-80ca-ec0db75cf8d8',
  name: 'ew famously minus',
};

export const sampleWithPartialData: ITag = {
  id: '632e4f02-c471-435c-85d3-a30d95ac02f8',
  name: 'boohoo tattered',
};

export const sampleWithFullData: ITag = {
  id: '2d328274-4c48-4481-a41e-1aca160383d3',
  name: 'yum',
};

export const sampleWithNewData: NewTag = {
  name: 'often uh-huh other',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
