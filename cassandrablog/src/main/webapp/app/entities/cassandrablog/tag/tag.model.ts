export interface ITag {
  id: string;
  name?: string | null;
  description?: string | null;
}

export type NewTag = Omit<ITag, 'id'> & { id: string };
