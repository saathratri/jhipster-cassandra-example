import { IBlog, NewBlog } from './blog.model';

export const sampleWithRequiredData: IBlog = {
  category: '4da996df-b750-45c1-9119-6c6e75473be8',
  handle: 'rout especially',
  content: 'gee bookend',
};

export const sampleWithPartialData: IBlog = {
  category: '76f87520-3b80-48b1-a99e-7e0e735f47d1',
  handle: 'apud gullible',
  content: 'enthusiastically when huzzah',
};

export const sampleWithFullData: IBlog = {
  category: '87ca6360-7501-4d68-a5d1-7975f7f1ec6f',
  blogId: 'eb8acab8-0908-4f0c-b838-da296859c1c5',
  handle: 'steak tackle haircut',
  content: 'mantua instead as',
};

export const sampleWithNewData: NewBlog = {
  handle: 'however during',
  content: 'quietly',
  category: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
