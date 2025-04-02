import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: 'b40d24fa-4b12-4fda-8bd2-83e21ed81201',
  title: 'ew',
  price: 29078,
  addedDate: 29561,
};

export const sampleWithPartialData: IProduct = {
  id: '74544479-133b-4de6-9440-cadbda812f56',
  title: 'kookily',
  price: 14316.53,
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  addedDate: 9463,
};

export const sampleWithFullData: IProduct = {
  id: 'e11e4696-4c2c-493b-bfee-87393412b120',
  title: 'shameless',
  price: 17307.26,
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  addedDate: 26959,
};

export const sampleWithNewData: NewProduct = {
  title: 'whose caption exempt',
  price: 1775.05,
  addedDate: 8357,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
