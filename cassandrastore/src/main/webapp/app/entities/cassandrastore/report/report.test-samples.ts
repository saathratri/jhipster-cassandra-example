import dayjs from 'dayjs/esm';

import { IReport, NewReport } from './report.model';

export const sampleWithRequiredData: IReport = {
  id: 'sample-id-1',
};

export const sampleWithPartialData: IReport = {
  id: 'sample-id-2',
  fileName: 'sample-fileName-2',
  fileExtension: 'sample-fileExtension-2',
  createDate: dayjs('2024-01-03T12:00:00Z'),
};

export const sampleWithFullData: IReport = {
  id: 'sample-id-3',
  fileName: 'sample-fileName-3',
  fileExtension: 'sample-fileExtension-3',
  createDate: dayjs('2024-01-04T12:00:00Z'),
  file: 'sample-file-3',
  approved: false,
};

export const sampleWithNewData: NewReport = {
  id: 'sample-id-4',
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
