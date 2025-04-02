import dayjs from 'dayjs/esm';

export interface IPost {
  compositeId: IPostId;
  title?: string | null;
  content?: string | null;
  publishedDateTime?: dayjs.Dayjs | null;
  sentDate?: dayjs.Dayjs | null;
}
export interface IPostId {
  createdDate: dayjs.Dayjs | null;
  addedDateTime: dayjs.Dayjs | null;
  postId: string | null;
}

export type NewPost = Omit<IPost, 'compositeId'> & { compositeId: IPostId };
