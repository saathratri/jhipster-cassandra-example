import { IPost, NewPost } from './post.model';

export const sampleWithRequiredData: IPost = {
  createdDate: 8730,
  title: 'lawful above unless',
  content: 'save',
};

export const sampleWithPartialData: IPost = {
  createdDate: 8720,
  title: 'trick ugh',
  content: 'vivaciously including replicate',
  publishedDateTime: 19879,
};

export const sampleWithFullData: IPost = {
  createdDate: 9917,
  addedDateTime: 32632,
  postId: '9013c01b-47f4-4c3c-b6f7-5f65ddd7eff0',
  title: 'daily infinite',
  content: 'gloat',
  publishedDateTime: 23544,
  sentDate: 2539,
};

export const sampleWithNewData: NewPost = {
  title: 'willfully settler',
  content: 'consequently',
  createdDate: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
