export interface ISetEntityByOrganization {
  organizationId: string;
  tags?: Set<string> | null;
}

export type NewSetEntityByOrganization = Omit<ISetEntityByOrganization, 'organizationId'> & { organizationId: string };
