import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISaathratriEntity4, NewSaathratriEntity4 } from '../saathratri-entity-4.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { compositeId: { organizationId: unknown; attributeKey: unknown } }> = Partial<
  Omit<T, 'compositeId'>
> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISaathratriEntity4 for edit and NewSaathratriEntity4FormGroupInput for create.
 */
type SaathratriEntity4FormGroupInput = ISaathratriEntity4 | PartialWithRequiredKeyOf<NewSaathratriEntity4>;

type SaathratriEntity4FormDefaults = Pick<NewSaathratriEntity4, 'compositeId'>;

type SaathratriEntity4FormGroupContent = {
  compositeId: FormGroup<{
    organizationId: FormControl<ISaathratriEntity4['compositeId']['organizationId']>;
    attributeKey: FormControl<ISaathratriEntity4['compositeId']['attributeKey']>;
  }>;
  attributeValue: FormControl<ISaathratriEntity4['attributeValue']>;
};

export type SaathratriEntity4FormGroup = FormGroup<SaathratriEntity4FormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SaathratriEntity4FormService {
  createSaathratriEntity4FormGroup(
    saathratriEntity4: SaathratriEntity4FormGroupInput = { compositeId: { organizationId: null, attributeKey: null } },
  ): SaathratriEntity4FormGroup {
    const saathratriEntity4RawValue = {
      ...this.getFormDefaults(),
      ...saathratriEntity4,
    };
    return new FormGroup<SaathratriEntity4FormGroupContent>({
      compositeId: new FormGroup({
        organizationId: new FormControl(
          {
            value: saathratriEntity4RawValue.compositeId.organizationId,
            disabled: saathratriEntity4RawValue.compositeId.organizationId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        attributeKey: new FormControl(
          {
            value: saathratriEntity4RawValue.compositeId.attributeKey,
            disabled: saathratriEntity4RawValue.compositeId.attributeKey !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      attributeValue: new FormControl(saathratriEntity4RawValue.attributeValue),
    });
  }

  getSaathratriEntity4(form: SaathratriEntity4FormGroup): ISaathratriEntity4 | NewSaathratriEntity4 {
    return form.getRawValue() as ISaathratriEntity4 | NewSaathratriEntity4;
  }

  resetForm(form: SaathratriEntity4FormGroup, saathratriEntity4: SaathratriEntity4FormGroupInput): void {
    const saathratriEntity4RawValue = { ...this.getFormDefaults(), ...saathratriEntity4 };
    form.reset(
      {
        ...saathratriEntity4RawValue,
        compositeId: {
          organizationId: { value: saathratriEntity4RawValue.compositeId.organizationId, disabled: true },
          attributeKey: { value: saathratriEntity4RawValue.compositeId.attributeKey, disabled: true },
        },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SaathratriEntity4FormDefaults {
    return {
      compositeId: {
        organizationId: null,
        attributeKey: null,
      },
    };
  }
}
