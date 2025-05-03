import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAddOnsAvailableByOrganization, NewAddOnsAvailableByOrganization } from '../add-ons-available-by-organization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<
  T extends { compositeId: { organizationId: unknown; entityType: unknown; entityId: unknown; addOnId: unknown } },
> = Partial<Omit<T, 'compositeId'>> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAddOnsAvailableByOrganization for edit and NewAddOnsAvailableByOrganizationFormGroupInput for create.
 */
type AddOnsAvailableByOrganizationFormGroupInput =
  | IAddOnsAvailableByOrganization
  | PartialWithRequiredKeyOf<NewAddOnsAvailableByOrganization>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAddOnsAvailableByOrganization | NewAddOnsAvailableByOrganization> = Omit<T, 'addOnDetailsBigInt'> & {
  addOnDetailsBigInt: Record<string, string> | null | undefined;
};

type AddOnsAvailableByOrganizationFormRawValue = FormValueOf<IAddOnsAvailableByOrganization>;

type NewAddOnsAvailableByOrganizationFormRawValue = FormValueOf<NewAddOnsAvailableByOrganization>;

type AddOnsAvailableByOrganizationFormDefaults = Pick<
  NewAddOnsAvailableByOrganization,
  'addOnDetailsBoolean' | 'addOnDetailsBigInt' | 'compositeId'
>;

type AddOnsAvailableByOrganizationFormGroupContent = {
  compositeId: FormGroup<{
    organizationId: FormControl<AddOnsAvailableByOrganizationFormRawValue['compositeId']['organizationId']>;
    entityType: FormControl<AddOnsAvailableByOrganizationFormRawValue['compositeId']['entityType']>;
    entityId: FormControl<AddOnsAvailableByOrganizationFormRawValue['compositeId']['entityId']>;
    addOnId: FormControl<AddOnsAvailableByOrganizationFormRawValue['compositeId']['addOnId']>;
  }>;
  addOnType: FormControl<AddOnsAvailableByOrganizationFormRawValue['addOnType']>;
  addOnDetailsText: FormControl<AddOnsAvailableByOrganizationFormRawValue['addOnDetailsText']>;
  addOnDetailsDecimal: FormControl<AddOnsAvailableByOrganizationFormRawValue['addOnDetailsDecimal']>;
  addOnDetailsBoolean: FormControl<AddOnsAvailableByOrganizationFormRawValue['addOnDetailsBoolean']>;
  addOnDetailsBigInt: FormControl<AddOnsAvailableByOrganizationFormRawValue['addOnDetailsBigInt']>;
};

export type AddOnsAvailableByOrganizationFormGroup = FormGroup<AddOnsAvailableByOrganizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AddOnsAvailableByOrganizationFormService {
  createAddOnsAvailableByOrganizationFormGroup(
    addOnsAvailableByOrganization: AddOnsAvailableByOrganizationFormGroupInput = {
      compositeId: { organizationId: null, entityType: null, entityId: null, addOnId: null },
    },
  ): AddOnsAvailableByOrganizationFormGroup {
    const addOnsAvailableByOrganizationRawValue = this.convertAddOnsAvailableByOrganizationToAddOnsAvailableByOrganizationRawValue({
      ...this.getFormDefaults(),
      ...addOnsAvailableByOrganization,
    });
    return new FormGroup<AddOnsAvailableByOrganizationFormGroupContent>({
      compositeId: new FormGroup({
        organizationId: new FormControl(
          {
            value: addOnsAvailableByOrganizationRawValue.compositeId.organizationId,
            disabled: addOnsAvailableByOrganizationRawValue.compositeId.organizationId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        entityType: new FormControl(
          {
            value: addOnsAvailableByOrganizationRawValue.compositeId.entityType,
            disabled: addOnsAvailableByOrganizationRawValue.compositeId.entityType !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        entityId: new FormControl(
          {
            value: addOnsAvailableByOrganizationRawValue.compositeId.entityId,
            disabled: addOnsAvailableByOrganizationRawValue.compositeId.entityId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        addOnId: new FormControl(
          {
            value: addOnsAvailableByOrganizationRawValue.compositeId.addOnId,
            disabled: addOnsAvailableByOrganizationRawValue.compositeId.addOnId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      addOnType: new FormControl(addOnsAvailableByOrganizationRawValue.addOnType),
      addOnDetailsText: new FormControl(addOnsAvailableByOrganizationRawValue.addOnDetailsText),
      addOnDetailsDecimal: new FormControl(addOnsAvailableByOrganizationRawValue.addOnDetailsDecimal),
      addOnDetailsBoolean: new FormControl(addOnsAvailableByOrganizationRawValue.addOnDetailsBoolean),
      addOnDetailsBigInt: new FormControl(addOnsAvailableByOrganizationRawValue.addOnDetailsBigInt),
    });
  }

  getAddOnsAvailableByOrganization(
    form: AddOnsAvailableByOrganizationFormGroup,
  ): IAddOnsAvailableByOrganization | NewAddOnsAvailableByOrganization {
    return this.convertAddOnsAvailableByOrganizationRawValueToAddOnsAvailableByOrganization(
      form.getRawValue() as AddOnsAvailableByOrganizationFormRawValue | NewAddOnsAvailableByOrganizationFormRawValue,
    );
  }

  resetForm(
    form: AddOnsAvailableByOrganizationFormGroup,
    addOnsAvailableByOrganization: AddOnsAvailableByOrganizationFormGroupInput,
  ): void {
    const addOnsAvailableByOrganizationRawValue = this.convertAddOnsAvailableByOrganizationToAddOnsAvailableByOrganizationRawValue({
      ...this.getFormDefaults(),
      ...addOnsAvailableByOrganization,
    });
    form.reset(
      {
        ...addOnsAvailableByOrganizationRawValue,
        compositeId: {
          organizationId: { value: addOnsAvailableByOrganizationRawValue.compositeId.organizationId, disabled: true },
          entityType: { value: addOnsAvailableByOrganizationRawValue.compositeId.entityType, disabled: true },
          entityId: { value: addOnsAvailableByOrganizationRawValue.compositeId.entityId, disabled: true },
          addOnId: { value: addOnsAvailableByOrganizationRawValue.compositeId.addOnId, disabled: true },
        },
        addOnDetailsBigInt: addOnsAvailableByOrganizationRawValue.addOnDetailsBigInt,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AddOnsAvailableByOrganizationFormDefaults {
    return {
      compositeId: {
        organizationId: null,
        entityType: null,
        entityId: null,
        addOnId: null,
      },
    };
  }

  private convertAddOnsAvailableByOrganizationRawValueToAddOnsAvailableByOrganization(
    rawAddOnsAvailableByOrganization: AddOnsAvailableByOrganizationFormRawValue | NewAddOnsAvailableByOrganizationFormRawValue,
  ): IAddOnsAvailableByOrganization | NewAddOnsAvailableByOrganization {
    return {
      ...rawAddOnsAvailableByOrganization,
      compositeId: {
        ...rawAddOnsAvailableByOrganization.compositeId,
      },
      addOnDetailsBigInt: rawAddOnsAvailableByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(
            Object.entries(rawAddOnsAvailableByOrganization.addOnDetailsBigInt).map(([key, value]) => [
              key,
              dayjs(value, DATE_TIME_FORMAT),
            ]),
          )
        : {},
    };
  }

  private convertAddOnsAvailableByOrganizationToAddOnsAvailableByOrganizationRawValue(
    addOnsAvailableByOrganization:
      | IAddOnsAvailableByOrganization
      | (Partial<NewAddOnsAvailableByOrganization> & AddOnsAvailableByOrganizationFormDefaults),
  ): AddOnsAvailableByOrganizationFormRawValue | PartialWithRequiredKeyOf<NewAddOnsAvailableByOrganizationFormRawValue> {
    return {
      ...addOnsAvailableByOrganization,
      compositeId: {
        ...addOnsAvailableByOrganization.compositeId,
      },
      addOnDetailsBigInt: addOnsAvailableByOrganization.addOnDetailsBigInt
        ? Object.fromEntries(
            Object.entries(addOnsAvailableByOrganization.addOnDetailsBigInt).map(([key, value]) => [key, value.format(DATE_TIME_FORMAT)]),
          )
        : {},
    };
  }
}
