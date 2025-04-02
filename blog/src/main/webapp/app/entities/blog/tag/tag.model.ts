export interface ITag {
  id: string;
  name?: string | null;
}

export type NewTag = Omit<ITag, 'id'> & { id: string };
