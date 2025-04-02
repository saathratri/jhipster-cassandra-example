export interface ISaathratriEntity {
  entityId: string;
  entityName?: string | null;
  entityDescription?: string | null;
  entityCost?: number | null;
  createdId?: string | null;
  createdTimeId?: string | null;
}

export type NewSaathratriEntity = Omit<ISaathratriEntity, 'entityId'> & { entityId: string };
