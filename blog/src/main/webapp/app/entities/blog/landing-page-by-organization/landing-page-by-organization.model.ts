import dayjs from 'dayjs/esm';

export interface ILandingPageByOrganization {
  organizationId: string;
  detailsText?: Record<string, string> | null;
  detailsDecimal?: Record<string, number> | null;
  detailsBoolean?: Record<string, boolean> | null;
  detailsBigInt?: Record<string, dayjs.Dayjs> | null;
}

export type NewLandingPageByOrganization = Omit<ILandingPageByOrganization, 'organizationId'> & { organizationId: string };
