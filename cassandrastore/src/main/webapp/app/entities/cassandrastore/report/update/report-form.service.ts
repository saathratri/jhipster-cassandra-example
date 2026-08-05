import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IReport, NewReport } from '../report.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReport for edit and NewReportFormGroupInput for create.
 */
type ReportFormGroupInput = IReport | PartialWithRequiredKeyOf<NewReport>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IReport | NewReport> = Omit<T, 'createDate'> & {
  createDate?: string | null;
};

type ReportFormRawValue = FormValueOf<IReport>;

type NewReportFormRawValue = FormValueOf<NewReport>;

type ReportFormDefaults = Pick<NewReport, 'id' | 'createDate' | 'approved'>;

type ReportFormGroupContent = {
  id: FormControl<ReportFormRawValue['id']>;
  fileName: FormControl<ReportFormRawValue['fileName']>;
  fileExtension: FormControl<ReportFormRawValue['fileExtension']>;
  createDate: FormControl<ReportFormRawValue['createDate']>;
  file: FormControl<ReportFormRawValue['file']>;
  fileContentType: FormControl<ReportFormRawValue['fileContentType']>;
  approved: FormControl<ReportFormRawValue['approved']>;
};

export type ReportFormGroup = FormGroup<ReportFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReportFormService {
  createReportFormGroup(report: ReportFormGroupInput = { id: '' }): ReportFormGroup {
    const reportRawValue = this.convertReportToReportRawValue({
      ...this.getFormDefaults(),
      ...report,
    });
    return new FormGroup<ReportFormGroupContent>({
      id: new FormControl(
        { value: reportRawValue.id, disabled: reportRawValue.id !== '' },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fileName: new FormControl(reportRawValue.fileName, {
        validators: [Validators.required],
      }),
      fileExtension: new FormControl(reportRawValue.fileExtension, {
        validators: [Validators.required],
      }),
      createDate: new FormControl(reportRawValue.createDate, {
        validators: [Validators.required],
      }),
      file: new FormControl(reportRawValue.file),
      fileContentType: new FormControl(reportRawValue.fileContentType),
      approved: new FormControl(reportRawValue.approved),
    });
  }

  getReport(form: ReportFormGroup): IReport | NewReport {
    return this.convertReportRawValueToReport(form.getRawValue() as ReportFormRawValue | NewReportFormRawValue);
  }

  resetForm(form: ReportFormGroup, report: ReportFormGroupInput): void {
    const reportRawValue = this.convertReportToReportRawValue({ ...this.getFormDefaults(), ...report });
    form.reset(
      {
        ...reportRawValue,
        id: { value: reportRawValue.id, disabled: true },
        createDate: reportRawValue.createDate,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ReportFormDefaults {
    return {
      id: '',
      approved: false,
    };
  }

  private convertReportRawValueToReport(rawReport: ReportFormRawValue | NewReportFormRawValue): IReport | NewReport {
    return {
      ...rawReport,
      createDate: typeof rawReport.createDate === 'number' ? dayjs(rawReport.createDate) : dayjs(rawReport.createDate, DATE_TIME_FORMAT),
    };
  }

  private convertReportToReportRawValue(
    report: IReport | (Partial<NewReport> & ReportFormDefaults),
  ): ReportFormRawValue | PartialWithRequiredKeyOf<NewReportFormRawValue> {
    return {
      ...report,
      createDate: report.createDate ? report.createDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
