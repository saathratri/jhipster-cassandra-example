import dayjs from 'dayjs/esm';

export interface ISaathratriEntity6 {
  compositeId: ISaathratriEntity6Id;
  departureDate?: dayjs.Dayjs | null;
  customerId?: string | null;
  customerFirstName?: string | null;
  customerLastName?: string | null;
  customerUpdatedEmail?: string | null;
  customerUpdatedPhoneNumber?: string | null;
  customerEstimatedArrivalTime?: string | null;
  tinyUrlShortCode?: string | null;
  addOnDetailsText?: Record<string, string> | null;
  addOnDetailsDecimal?: Record<string, number> | null;
  addOnDetailsBoolean?: Record<string, boolean> | null;
  addOnDetailsBigInt?: Record<string, dayjs.Dayjs> | null;
}
export interface ISaathratriEntity6Id {
  organizationId: string | null;
  arrivalDate: dayjs.Dayjs | null;
  accountNumber: string | null;
  createdTimeId: string | null;
}

export type NewSaathratriEntity6 = Omit<ISaathratriEntity6, 'compositeId'> & { compositeId: ISaathratriEntity6Id };
