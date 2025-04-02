import dayjs from 'dayjs/esm';

export interface ISaathratriEntity5 {
  compositeId: ISaathratriEntity5Id;
  addOnType?: string | null;
  addOnDetailsText?: Record<string, string> | null;
  addOnDetailsDecimal?: Record<string, number> | null;
  addOnDetailsBoolean?: Record<string, boolean> | null;
  addOnDetailsBigInt?: Record<string, dayjs.Dayjs> | null;
}
export interface ISaathratriEntity5Id {
  organizationId: string | null;
  entityType: string | null;
  entityId: string | null;
  addOnId: string | null;
}

export type NewSaathratriEntity5 = Omit<ISaathratriEntity5, 'compositeId'> & { compositeId: ISaathratriEntity5Id };
