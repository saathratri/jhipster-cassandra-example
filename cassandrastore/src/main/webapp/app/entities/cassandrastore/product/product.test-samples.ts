import dayjs from 'dayjs/esm';

import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: 'sample-id-1',
};

export const sampleWithPartialData: IProduct = {
  id: 'sample-id-2',
  title: 'sample-title-2',
  price: 1002,
};

export const sampleWithFullData: IProduct = {
  id: 'sample-id-3',
  title: 'sample-title-3',
  price: 1003,
  image: 'sample-image-3',
  addedDate: dayjs('2024-01-04T12:00:00Z'),
};

export const sampleWithNewData: NewProduct = {
  id: 'sample-id-4',
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
