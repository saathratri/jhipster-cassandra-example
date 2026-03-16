export interface ISaathratriEntity4 {
  compositeId: ISaathratriEntity4Id;
  attributeValue?: string | null;
}
export interface ISaathratriEntity4Id {
  organizationId: string | null;
  attributeKey: string | null;
}

export type NewSaathratriEntity4 = Omit<ISaathratriEntity4, 'compositeId'> & { compositeId: ISaathratriEntity4Id };
