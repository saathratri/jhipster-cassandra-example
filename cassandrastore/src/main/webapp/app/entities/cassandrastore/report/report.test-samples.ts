import { IReport, NewReport } from './report.model';

export const sampleWithRequiredData: IReport = {
  id: '49428f42-cbc0-4f30-8a83-27dae5b5d5a5',
  fileName: 'than gosh',
  fileExtension: 'radiant brr',
  createDate: 12851,
};

export const sampleWithPartialData: IReport = {
  id: 'd55c438e-22da-4b11-99de-d57a6bed80db',
  fileName: 'whether unaccountably riser',
  fileExtension: 'stunt handsome',
  createDate: 14400,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
};

export const sampleWithFullData: IReport = {
  id: 'f239ef39-ffd7-44d7-92fa-771d957cc0be',
  fileName: 'so miserable indeed',
  fileExtension: 'till though',
  createDate: 7734,
  file: '../fake-data/blob/hipster.png',
  fileContentType: 'unknown',
  approved: false,
};

export const sampleWithNewData: NewReport = {
  fileName: 'grass',
  fileExtension: 'provided beneath while',
  createDate: 20057,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
