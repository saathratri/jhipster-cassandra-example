import { ISaathratriEntity, NewSaathratriEntity } from './saathratri-entity.model';

export const sampleWithRequiredData: ISaathratriEntity = {
  entityId: 'abbbcbc7-233d-4336-938e-4f24eb9b13b2',
};

export const sampleWithPartialData: ISaathratriEntity = {
  entityId: 'b4244c61-ea74-4f78-97a2-422e3c6c20d9',
  entityName: 'finally anti hundred',
  entityDescription: 'conversation inasmuch',
};

export const sampleWithFullData: ISaathratriEntity = {
  entityId: 'cfbca84d-829a-4f2f-9007-f0c44401398c',
  entityName: 'longingly sign',
  entityDescription: 'healthily greatly brr',
  entityCost: 11466.15,
  createdId: '60db336f-4188-450a-899e-f4619cbfdd82',
  createdTimeId: '450224e1-6212-407e-9ec9-e52679b9f7bf',
};

export const sampleWithNewData: NewSaathratriEntity = {
  entityId: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
