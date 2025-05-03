import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAddOnsSelectedByOrganization, NewAddOnsSelectedByOrganization } from '../add-ons-selected-by-organization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<
  T extends { compositeId: { organizationId: unknown; arrivalDate: unknown; accountNumber: unknown; createdTimeId: unknown } },
> = Partial<Omit<T, 'compositeId'>> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAddOnsSelectedByOrganization for edit and NewAddOnsSelectedByOrganizationFormGroupInput for create.
 */
type AddOnsSelectedByOrganizationFormGroupInput = IAddOnsSelectedByOrganization | PartialWithRequiredKeyOf<NewAddOnsSelectedByOrganization>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAddOnsSelectedByOrganization | NewAddOnsSelectedByOrganization> = Omit<T, 'addOnDetailsBigInt'> & {
  addOnDetailsBigInt: Record<string, string> | null | undefined;
};

type AddOnsSelectedByOrganizationFormRawValue = FormValueOf<IAddOnsSelectedByOrganization>;

type NewAddOnsSelectedByOrganizationFormRawValue = FormValueOf<NewAddOnsSelectedByOrganization>;

type AddOnsSelectedByOrganizationFormDefaults = Pick<
  NewAddOnsSelectedByOrganization,
  'addOnDetailsBoolean' | 'addOnDetailsBigInt' | 'compositeId'
>;

type AddOnsSelectedByOrganizationFormGroupContent = {
  compositeId: FormGroup<{
    organizationId: FormControl<AddOnsSelectedByOrganizationFormRawValue['compositeId']['organizationId']>;
    arrivalDate: FormControl<AddOnsSelectedByOrganizationFormRawValue['compositeId']['arrivalDate']>;
    accountNumber: FormControl<AddOnsSelectedByOrganizationFormRawValue['compositeId']['accountNumber']>;
    createdTimeId: FormControl<AddOnsSelectedByOrganizationFormRawValue['compositeId']['createdTimeId']>;
  }>;
  departureDate: FormControl<AddOnsSelectedByOrganizationFormRawValue['departureDate']>;
  customerId: FormControl<AddOnsSelectedByOrganizationFormRawValue['customerId']>;
  customerFirstName: FormControl<AddOnsSelectedByOrganizationFormRawValue['customerFirstName']>;
  customerLastName: FormControl<AddOnsSelectedByOrganizationFormRawValue['customerLastName']>;
  customerUpdatedEmail: FormControl<AddOnsSelectedByOrganizationFormRawValue['customerUpdatedEmail']>;
  customerUpdatedPhoneNumber: FormControl<AddOnsSelectedByOrganizationFormRawValue['customerUpdatedPhoneNumber']>;
  customerEstimatedArrivalTime: FormControl<AddOnsSelectedByOrganizationFormRawValue['customerEstimatedArrivalTime']>;
  tinyUrlShortCode: FormControl<AddOnsSelectedByOrganizationFormRawValue['tinyUrlShortCode']>;
  addOnDetailsText: FormControl<AddOnsSelectedByOrganizationFormRawValue['addOnDetailsText']>;
  addOnDetailsDecimal: FormControl<AddOnsSelectedByOrganizationFormRawValue['addOnDetailsDecimal']>;
  addOnDetailsBoolean: FormControl<AddOnsSelectedByOrganizationFormRawValue['addOnDetailsBoolean']>;
  addOnDetailsBigInt: FormControl<AddOnsSelectedByOrganizationFormRawValue['addOnDetailsBigInt']>;
};

export type AddOnsSelectedByOrganizationFormGroup = FormGroup<AddOnsSelectedByOrganizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AddOnsSelectedByOrganizationFormService {
  createAddOnsSelectedByOrganizationFormGroup(
    addOnsSelectedByOrganization: AddOnsSelectedByOrganizationFormGroupInput = {
      compositeId: { organizationId: null, arrivalDate: null, accountNumber: null, createdTimeId: null },
    },
  ): AddOnsSelectedByOrganizationFormGroup {
    const addOnsSelectedByOrganizationRawValue = this.convertAddOnsSelectedByOrganizationToAddOnsSelectedByOrganizationRawValue({
      ...this.getFormDefaults(),
      ...addOnsSelectedByOrganization,
    });
    return new FormGroup<AddOnsSelectedByOrganizationFormGroupContent>({
      compositeId: new FormGroup({
        organizationId: new FormControl(
          {
            value: addOnsSelectedByOrganizationRawValue.compositeId.organizationId,
            disabled: addOnsSelectedByOrganizationRawValue.compositeId.organizationId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        arrivalDate: new FormControl(
          {
            value: addOnsSelectedByOrganizationRawValue.compositeId.arrivalDate,
            disabled: addOnsSelectedByOrganizationRawValue.compositeId.arrivalDate !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        accountNumber: new FormControl(
          {
            value: addOnsSelectedByOrganizationRawValue.compositeId.accountNumber,
            disabled: addOnsSelectedByOrganizationRawValue.compositeId.accountNumber !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        createdTimeId: new FormControl(
          {
            value: addOnsSelectedByOrganizationRawValue.compositeId.createdTimeId,
            disabled: addOnsSelectedByOrganizationRawValue.compositeId.createdTimeId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      departureDate: new FormControl(addOnsSelectedByOrganizationRawValue.departureDate),
      customerId: new FormControl(addOnsSelectedByOrganizationRawValue.customerId),
      customerFirstName: new FormControl(addOnsSelectedByOrganizationRawValue.customerFirstName),
      customerLastName: new FormControl(addOnsSelectedByOrganizationRawValue.customerLastName),
      customerUpdatedEmail: new FormControl(addOnsSelectedByOrganizationRawValue.customerUpdatedEmail),
      customerUpdatedPhoneNumber: new FormControl(addOnsSelectedByOrganizationRawValue.customerUpdatedPhoneNumber),
      customerEstimatedArrivalTime: new FormControl(addOnsSelectedByOrganizationRawValue.customerEstimatedArrivalTime),
      tinyUrlShortCode: new FormControl(addOnsSelectedByOrganizationRawValue.tinyUrlShortCode),
      addOnDetailsText: new FormControl(addOnsSelectedByOrganizationRawValue.addOnDetailsText),
      addOnDetailsDecimal: new FormControl(addOnsSelectedByOrganizationRawValue.addOnDetailsDecimal),
      addOnDetailsBoolean: new FormControl(addOnsSelectedByOrganizationRawValue.addOnDetailsBoolean),
      addOnDetailsBigInt: new FormControl(addOnsSelectedByOrganizationRawValue.addOnDetailsBigInt),
    });
  }

  getAddOnsSelectedByOrganization(
    form: AddOnsSelectedByOrganizationFormGroup,
  ): IAddOnsSelectedByOrganization | NewAddOnsSelectedByOrganization {
    return this.convertAddOnsSelectedByOrganizationRawValueToAddOnsSelectedByOrganization(
      form.getRawValue() as AddOnsSelectedByOrganizationFormRawValue | NewAddOnsSelectedByOrganizationFormRawValue,
    );
  }

  resetForm(form: AddOnsSelectedByOrganizationFormGroup, addOnsSelectedByOrganization: AddOnsSelectedByOrganizationFormGroupInput): void {
    const addOnsSelectedByOrganizationRawValue = this.convertAddOnsSelectedByOrganizationToAddOnsSelectedByOrganizationRawValue({
      ...this.getFormDefaults(),
      ...addOnsSelectedByOrganization,
    });
    form.reset(
      {
        ...addOnsSelectedByOrganizationRawValue,
        compositeId: {
          organizationId: { value: addOnsSelectedByOrganizationRawValue.compositeId.organizationId, disabled: true },
          arrivalDate: { value: addOnsSelectedByOrganizationRawValue.compositeId.arrivalDate?.toDate(), disabled: true },
          accountNumber: { value: addOnsSelectedByOrganizationRawValue.compositeId.accountNumber, disabled: true },
          createdTimeId: { value: addOnsSelectedByOrganizationRawValue.compositeId.createdTimeId, disabled: true },
        },
        departureDate: addOnsSelectedByOrganizationRawValue.departureDate?.toDate(),
        addOnDetailsBigInt: addOnsSelectedByOrganizationRawValue.addOnDetailsBigInt,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AddOnsSelectedByOrganizationFormDefaults {
    return {
      compositeId: {
        organizationId: null,
        arrivalDate: null,
        accountNumber: null,
        createdTimeId: null,
      },
    };
  }

  private convertAddOnsSelectedByOrganizationRawValueToAddOnsSelectedByOrganization(
    rawAddOnsSelectedByOrganization: AddOnsSelectedByOrganizationFormRawValue | NewAddOnsSelectedByOrganizationFormRawValue,
  ): IAddOnsSelectedByOrganization | NewAddOnsSelectedByOrganization {
    return {
      ...rawAddOnsSelectedByOrganization,
      compositeId: {
        ...rawAddOnsSelectedByOrganization.compositeId,
      },
      addOnDetailsBigInt: rawAddOnsSelectedByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(
            Object.entries(rawAddOnsSelectedByOrganization.addOnDetailsBigInt).map(([key, value]) => [key, dayjs(value, DATE_TIME_FORMAT)]),
          )
        : {},
    };
  }

  private convertAddOnsSelectedByOrganizationToAddOnsSelectedByOrganizationRawValue(
    addOnsSelectedByOrganization:
      | IAddOnsSelectedByOrganization
      | (Partial<NewAddOnsSelectedByOrganization> & AddOnsSelectedByOrganizationFormDefaults),
  ): AddOnsSelectedByOrganizationFormRawValue | PartialWithRequiredKeyOf<NewAddOnsSelectedByOrganizationFormRawValue> {
    return {
      ...addOnsSelectedByOrganization,
      compositeId: {
        ...addOnsSelectedByOrganization.compositeId,
      },
      addOnDetailsBigInt: addOnsSelectedByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(
            Object.entries(addOnsSelectedByOrganization.addOnDetailsBigInt).map(([key, value]) => [key, value.format(DATE_TIME_FORMAT)]),
          )
        : {},
    };
  }
}
