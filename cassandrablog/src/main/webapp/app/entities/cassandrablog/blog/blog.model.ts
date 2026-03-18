export interface IBlog {
  compositeId: IBlogId;
  handle?: string | null;
  content?: string | null;
}
export interface IBlogId {
  category: string | null;
  blogId: string | null;
}

export type NewBlog = Omit<IBlog, 'compositeId'> & { compositeId: IBlogId };
