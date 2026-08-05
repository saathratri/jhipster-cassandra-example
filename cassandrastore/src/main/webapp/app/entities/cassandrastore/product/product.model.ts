import dayjs from 'dayjs/esm';

export interface IProduct {
  id: string;
  title?: string | null;
  price?: number | null;
  image?: string | null;
  imageContentType?: string | null;
  addedDate?: dayjs.Dayjs | null;
}

export type NewProduct = Omit<IProduct, 'id'> & { id: string };
