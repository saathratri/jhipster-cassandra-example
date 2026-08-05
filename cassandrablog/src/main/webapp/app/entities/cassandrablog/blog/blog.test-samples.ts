import { IBlog, NewBlog } from './blog.model';

export const sampleWithRequiredData: IBlog = {
  compositeId: { category: 'sample-category-1', blogId: 'sample-blogId-1' },
};

export const sampleWithPartialData: IBlog = {
  compositeId: { category: 'sample-category-2', blogId: 'sample-blogId-2' },
  handle: 'sample-handle-2',
};

export const sampleWithFullData: IBlog = {
  compositeId: { category: 'sample-category-3', blogId: 'sample-blogId-3' },
  handle: 'sample-handle-3',
  content: 'sample-content-3',
};

export const sampleWithNewData: NewBlog = {
  compositeId: { category: 'sample-category-4', blogId: 'sample-blogId-4' },
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
