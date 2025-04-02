import { IPost, NewPost } from './post.model';

export const sampleWithRequiredData: IPost = {
  createdDate: 25339,
  title: 'father',
  content: 'scary',
};

export const sampleWithPartialData: IPost = {
  createdDate: 6774,
  postId: '01a92aab-49e7-4e79-b389-e56ab6d34aa4',
  title: 'far',
  content: 'achieve',
};

export const sampleWithFullData: IPost = {
  createdDate: 21628,
  addedDateTime: 11250,
  postId: '61405aa9-d3a2-43c1-bb88-73c3e9350336',
  title: 'shred frizz sleepy',
  content: 'towards in',
  publishedDateTime: 6720,
  sentDate: 17628,
};

export const sampleWithNewData: NewPost = {
  title: 'till',
  content: 'grumpy drat beside',
  createdDate: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
