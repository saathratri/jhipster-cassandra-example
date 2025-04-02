import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISaathratriEntity6, NewSaathratriEntity6 } from '../saathratri-entity-6.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<
  T extends { compositeId: { organizationId: unknown; arrivalDate: unknown; accountNumber: unknown; createdTimeId: unknown } },
> = Partial<Omit<T, 'compositeId'>> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISaathratriEntity6 for edit and NewSaathratriEntity6FormGroupInput for create.
 */
type SaathratriEntity6FormGroupInput = ISaathratriEntity6 | PartialWithRequiredKeyOf<NewSaathratriEntity6>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISaathratriEntity6 | NewSaathratriEntity6> = Omit<T, 'addOnDetailsBigInt'> & {
  addOnDetailsBigInt?: string | null;
};

type SaathratriEntity6FormRawValue = FormValueOf<ISaathratriEntity6>;

type NewSaathratriEntity6FormRawValue = FormValueOf<NewSaathratriEntity6>;

type SaathratriEntity6FormDefaults = Pick<NewSaathratriEntity6, 'addOnDetailsBoolean' | 'addOnDetailsBigInt' | 'compositeId'>;

type SaathratriEntity6FormGroupContent = {
  compositeId: FormGroup<{
    organizationId: FormControl<SaathratriEntity6FormRawValue['compositeId']['organizationId']>;
    arrivalDate: FormControl<SaathratriEntity6FormRawValue['compositeId']['arrivalDate']>;
    accountNumber: FormControl<SaathratriEntity6FormRawValue['compositeId']['accountNumber']>;
    createdTimeId: FormControl<SaathratriEntity6FormRawValue['compositeId']['createdTimeId']>;
  }>;
  departureDate: FormControl<SaathratriEntity6FormRawValue['departureDate']>;
  customerId: FormControl<SaathratriEntity6FormRawValue['customerId']>;
  customerFirstName: FormControl<SaathratriEntity6FormRawValue['customerFirstName']>;
  customerLastName: FormControl<SaathratriEntity6FormRawValue['customerLastName']>;
  customerUpdatedEmail: FormControl<SaathratriEntity6FormRawValue['customerUpdatedEmail']>;
  customerUpdatedPhoneNumber: FormControl<SaathratriEntity6FormRawValue['customerUpdatedPhoneNumber']>;
  customerEstimatedArrivalTime: FormControl<SaathratriEntity6FormRawValue['customerEstimatedArrivalTime']>;
  tinyUrlShortCode: FormControl<SaathratriEntity6FormRawValue['tinyUrlShortCode']>;
  addOnDetailsText: FormControl<SaathratriEntity6FormRawValue['addOnDetailsText']>;
  addOnDetailsDecimal: FormControl<SaathratriEntity6FormRawValue['addOnDetailsDecimal']>;
  addOnDetailsBoolean: FormControl<SaathratriEntity6FormRawValue['addOnDetailsBoolean']>;
  addOnDetailsBigInt: FormControl<SaathratriEntity6FormRawValue['addOnDetailsBigInt']>;
};

export type SaathratriEntity6FormGroup = FormGroup<SaathratriEntity6FormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SaathratriEntity6FormService {
  createSaathratriEntity6FormGroup(
    saathratriEntity6: SaathratriEntity6FormGroupInput = {
      compositeId: { organizationId: null, arrivalDate: null, accountNumber: null, createdTimeId: null },
    },
  ): SaathratriEntity6FormGroup {
    const saathratriEntity6RawValue = this.convertSaathratriEntity6ToSaathratriEntity6RawValue({
      ...this.getFormDefaults(),
      ...saathratriEntity6,
    });
    return new FormGroup<SaathratriEntity6FormGroupContent>({
      compositeId: new FormGroup({
        organizationId: new FormControl(
          {
            value: saathratriEntity6RawValue.compositeId.organizationId,
            disabled: saathratriEntity6RawValue.compositeId.organizationId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        arrivalDate: new FormControl(
          {
            value: saathratriEntity6RawValue.compositeId.arrivalDate,
            disabled: saathratriEntity6RawValue.compositeId.arrivalDate !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        accountNumber: new FormControl(
          {
            value: saathratriEntity6RawValue.compositeId.accountNumber,
            disabled: saathratriEntity6RawValue.compositeId.accountNumber !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        createdTimeId: new FormControl(
          {
            value: saathratriEntity6RawValue.compositeId.createdTimeId,
            disabled: saathratriEntity6RawValue.compositeId.createdTimeId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      departureDate: new FormControl(saathratriEntity6RawValue.departureDate),
      customerId: new FormControl(saathratriEntity6RawValue.customerId),
      customerFirstName: new FormControl(saathratriEntity6RawValue.customerFirstName),
      customerLastName: new FormControl(saathratriEntity6RawValue.customerLastName),
      customerUpdatedEmail: new FormControl(saathratriEntity6RawValue.customerUpdatedEmail),
      customerUpdatedPhoneNumber: new FormControl(saathratriEntity6RawValue.customerUpdatedPhoneNumber),
      customerEstimatedArrivalTime: new FormControl(saathratriEntity6RawValue.customerEstimatedArrivalTime),
      tinyUrlShortCode: new FormControl(saathratriEntity6RawValue.tinyUrlShortCode),
      addOnDetailsText: new FormControl(saathratriEntity6RawValue.addOnDetailsText),
      addOnDetailsDecimal: new FormControl(saathratriEntity6RawValue.addOnDetailsDecimal),
      addOnDetailsBoolean: new FormControl(saathratriEntity6RawValue.addOnDetailsBoolean),
      addOnDetailsBigInt: new FormControl(saathratriEntity6RawValue.addOnDetailsBigInt),
    });
  }

  getSaathratriEntity6(form: SaathratriEntity6FormGroup): ISaathratriEntity6 | NewSaathratriEntity6 {
    return this.convertSaathratriEntity6RawValueToSaathratriEntity6(
      form.getRawValue() as SaathratriEntity6FormRawValue | NewSaathratriEntity6FormRawValue,
    );
  }

  resetForm(form: SaathratriEntity6FormGroup, saathratriEntity6: SaathratriEntity6FormGroupInput): void {
    const saathratriEntity6RawValue = this.convertSaathratriEntity6ToSaathratriEntity6RawValue({
      ...this.getFormDefaults(),
      ...saathratriEntity6,
    });
    form.reset(
      {
        ...saathratriEntity6RawValue,
        compositeId: {
          organizationId: { value: saathratriEntity6RawValue.compositeId.organizationId, disabled: true },
          arrivalDate: { value: saathratriEntity6RawValue.compositeId.arrivalDate?.toDate(), disabled: true },
          accountNumber: { value: saathratriEntity6RawValue.compositeId.accountNumber, disabled: true },
          createdTimeId: { value: saathratriEntity6RawValue.compositeId.createdTimeId, disabled: true },
        },
        departureDate: saathratriEntity6RawValue.departureDate?.toDate(),
        addOnDetailsBigInt: saathratriEntity6RawValue.addOnDetailsBigInt,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SaathratriEntity6FormDefaults {
    return {
      compositeId: {
        organizationId: null,
        arrivalDate: null,
        accountNumber: null,
        createdTimeId: null,
      },
      addOnDetailsBoolean: false,
    };
  }

  private convertSaathratriEntity6RawValueToSaathratriEntity6(
    rawSaathratriEntity6: SaathratriEntity6FormRawValue | NewSaathratriEntity6FormRawValue,
  ): ISaathratriEntity6 | NewSaathratriEntity6 {
    return {
      ...rawSaathratriEntity6,
      compositeId: {
        ...rawSaathratriEntity6.compositeId,
      },
      addOnDetailsBigInt: dayjs(rawSaathratriEntity6.addOnDetailsBigInt, DATE_TIME_FORMAT),
    };
  }

  private convertSaathratriEntity6ToSaathratriEntity6RawValue(
    saathratriEntity6: ISaathratriEntity6 | (Partial<NewSaathratriEntity6> & SaathratriEntity6FormDefaults),
  ): SaathratriEntity6FormRawValue | PartialWithRequiredKeyOf<NewSaathratriEntity6FormRawValue> {
    return {
      ...saathratriEntity6,
      compositeId: {
        ...saathratriEntity6.compositeId,
      },
      addOnDetailsBigInt: saathratriEntity6.addOnDetailsBigInt ? saathratriEntity6.addOnDetailsBigInt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
