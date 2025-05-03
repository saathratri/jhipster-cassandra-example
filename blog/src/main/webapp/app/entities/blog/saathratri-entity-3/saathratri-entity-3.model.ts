import dayjs from 'dayjs/esm';

export interface ISaathratriEntity3 {
  compositeId: ISaathratriEntity3Id;
  entityName?: string | null;
  entityDescription?: string | null;
  entityCost?: number | null;
  departureDate?: dayjs.Dayjs | null;
  tags?: Set<string> | null;
}
export interface ISaathratriEntity3Id {
  entityType: string | null;
  createdTimeId: string | null;
}

export type NewSaathratriEntity3 = Omit<ISaathratriEntity3, 'compositeId'> & { compositeId: ISaathratriEntity3Id };
