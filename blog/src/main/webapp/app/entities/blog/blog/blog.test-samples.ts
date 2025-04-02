import { IBlog, NewBlog } from './blog.model';

export const sampleWithRequiredData: IBlog = {
  category: '63ee344c-2a31-4769-8c7a-aa9082fd6576',
  handle: 'haX',
  content: 'cute',
};

export const sampleWithPartialData: IBlog = {
  category: 'cf7121b7-200b-40fc-89b9-6ddc17ce9e4c',
  blogId: 'ed2e19bb-d6d5-4049-93ad-af974dafcdcb',
  handle: 'factorize',
  content: 'oh who',
};

export const sampleWithFullData: IBlog = {
  category: 'd2b85c96-20f7-4a8e-afea-3219a68dfa18',
  blogId: 'af5de289-e478-41bb-ada1-0724050d868b',
  handle: 'generously mainstream mobility',
  content: 'waver afterwards concerning',
};

export const sampleWithNewData: NewBlog = {
  handle: 'uh-huh range',
  content: 'unless consequently',
  category: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
