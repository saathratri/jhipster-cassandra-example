export interface ITag {
  id: string;
  name?: string | null;
  description?: string | null;
  nameEmbedding?: number[] | null;
  descriptionEmbedding?: number[] | null;
}

export type NewTag = Omit<ITag, 'id'> & { id: string };
