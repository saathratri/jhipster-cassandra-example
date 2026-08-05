import dayjs from 'dayjs/esm';

export interface IReport {
  id: string;
  fileName?: string | null;
  fileExtension?: string | null;
  createDate?: dayjs.Dayjs | null;
  file?: string | null;
  fileContentType?: string | null;
  approved?: boolean | null;
}

export type NewReport = Omit<IReport, 'id'> & { id: string };
