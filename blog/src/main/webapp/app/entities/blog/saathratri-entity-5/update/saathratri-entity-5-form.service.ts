import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISaathratriEntity5, NewSaathratriEntity5 } from '../saathratri-entity-5.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<
  T extends { compositeId: { organizationId: unknown; entityType: unknown; entityId: unknown; addOnId: unknown } },
> = Partial<Omit<T, 'compositeId'>> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISaathratriEntity5 for edit and NewSaathratriEntity5FormGroupInput for create.
 */
type SaathratriEntity5FormGroupInput = ISaathratriEntity5 | PartialWithRequiredKeyOf<NewSaathratriEntity5>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISaathratriEntity5 | NewSaathratriEntity5> = Omit<T, 'addOnDetailsBigInt'> & {
  addOnDetailsBigInt?: string | null;
};

type SaathratriEntity5FormRawValue = FormValueOf<ISaathratriEntity5>;

type NewSaathratriEntity5FormRawValue = FormValueOf<NewSaathratriEntity5>;

type SaathratriEntity5FormDefaults = Pick<NewSaathratriEntity5, 'addOnDetailsBoolean' | 'addOnDetailsBigInt' | 'compositeId'>;

type SaathratriEntity5FormGroupContent = {
  compositeId: FormGroup<{
    organizationId: FormControl<SaathratriEntity5FormRawValue['compositeId']['organizationId']>;
    entityType: FormControl<SaathratriEntity5FormRawValue['compositeId']['entityType']>;
    entityId: FormControl<SaathratriEntity5FormRawValue['compositeId']['entityId']>;
    addOnId: FormControl<SaathratriEntity5FormRawValue['compositeId']['addOnId']>;
  }>;
  addOnType: FormControl<SaathratriEntity5FormRawValue['addOnType']>;
  addOnDetailsText: FormControl<SaathratriEntity5FormRawValue['addOnDetailsText']>;
  addOnDetailsDecimal: FormControl<SaathratriEntity5FormRawValue['addOnDetailsDecimal']>;
  addOnDetailsBoolean: FormControl<SaathratriEntity5FormRawValue['addOnDetailsBoolean']>;
  addOnDetailsBigInt: FormControl<SaathratriEntity5FormRawValue['addOnDetailsBigInt']>;
};

export type SaathratriEntity5FormGroup = FormGroup<SaathratriEntity5FormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SaathratriEntity5FormService {
  createSaathratriEntity5FormGroup(
    saathratriEntity5: SaathratriEntity5FormGroupInput = {
      compositeId: { organizationId: null, entityType: null, entityId: null, addOnId: null },
    },
  ): SaathratriEntity5FormGroup {
    const saathratriEntity5RawValue = this.convertSaathratriEntity5ToSaathratriEntity5RawValue({
      ...this.getFormDefaults(),
      ...saathratriEntity5,
    });
    return new FormGroup<SaathratriEntity5FormGroupContent>({
      compositeId: new FormGroup({
        organizationId: new FormControl(
          {
            value: saathratriEntity5RawValue.compositeId.organizationId,
            disabled: saathratriEntity5RawValue.compositeId.organizationId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        entityType: new FormControl(
          { value: saathratriEntity5RawValue.compositeId.entityType, disabled: saathratriEntity5RawValue.compositeId.entityType !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        entityId: new FormControl(
          { value: saathratriEntity5RawValue.compositeId.entityId, disabled: saathratriEntity5RawValue.compositeId.entityId !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        addOnId: new FormControl(
          { value: saathratriEntity5RawValue.compositeId.addOnId, disabled: saathratriEntity5RawValue.compositeId.addOnId !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      addOnType: new FormControl(saathratriEntity5RawValue.addOnType),
      addOnDetailsText: new FormControl(saathratriEntity5RawValue.addOnDetailsText),
      addOnDetailsDecimal: new FormControl(saathratriEntity5RawValue.addOnDetailsDecimal),
      addOnDetailsBoolean: new FormControl(saathratriEntity5RawValue.addOnDetailsBoolean),
      addOnDetailsBigInt: new FormControl(saathratriEntity5RawValue.addOnDetailsBigInt),
    });
  }

  getSaathratriEntity5(form: SaathratriEntity5FormGroup): ISaathratriEntity5 | NewSaathratriEntity5 {
    return this.convertSaathratriEntity5RawValueToSaathratriEntity5(
      form.getRawValue() as SaathratriEntity5FormRawValue | NewSaathratriEntity5FormRawValue,
    );
  }

  resetForm(form: SaathratriEntity5FormGroup, saathratriEntity5: SaathratriEntity5FormGroupInput): void {
    const saathratriEntity5RawValue = this.convertSaathratriEntity5ToSaathratriEntity5RawValue({
      ...this.getFormDefaults(),
      ...saathratriEntity5,
    });
    form.reset(
      {
        ...saathratriEntity5RawValue,
        compositeId: {
          organizationId: { value: saathratriEntity5RawValue.compositeId.organizationId, disabled: true },
          entityType: { value: saathratriEntity5RawValue.compositeId.entityType, disabled: true },
          entityId: { value: saathratriEntity5RawValue.compositeId.entityId, disabled: true },
          addOnId: { value: saathratriEntity5RawValue.compositeId.addOnId, disabled: true },
        },
        addOnDetailsBigInt: saathratriEntity5RawValue.addOnDetailsBigInt,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SaathratriEntity5FormDefaults {
    return {
      compositeId: {
        organizationId: null,
        entityType: null,
        entityId: null,
        addOnId: null,
      },
      addOnDetailsBoolean: false,
    };
  }

  private convertSaathratriEntity5RawValueToSaathratriEntity5(
    rawSaathratriEntity5: SaathratriEntity5FormRawValue | NewSaathratriEntity5FormRawValue,
  ): ISaathratriEntity5 | NewSaathratriEntity5 {
    return {
      ...rawSaathratriEntity5,
      compositeId: {
        ...rawSaathratriEntity5.compositeId,
      },
      addOnDetailsBigInt: dayjs(rawSaathratriEntity5.addOnDetailsBigInt, DATE_TIME_FORMAT),
    };
  }

  private convertSaathratriEntity5ToSaathratriEntity5RawValue(
    saathratriEntity5: ISaathratriEntity5 | (Partial<NewSaathratriEntity5> & SaathratriEntity5FormDefaults),
  ): SaathratriEntity5FormRawValue | PartialWithRequiredKeyOf<NewSaathratriEntity5FormRawValue> {
    return {
      ...saathratriEntity5,
      compositeId: {
        ...saathratriEntity5.compositeId,
      },
      addOnDetailsBigInt: saathratriEntity5.addOnDetailsBigInt ? saathratriEntity5.addOnDetailsBigInt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
