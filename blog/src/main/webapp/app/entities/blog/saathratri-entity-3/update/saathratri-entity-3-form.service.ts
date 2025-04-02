import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISaathratriEntity3, NewSaathratriEntity3 } from '../saathratri-entity-3.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { compositeId: { entityType: unknown; createdTimeId: unknown } }> = Partial<
  Omit<T, 'compositeId'>
> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISaathratriEntity3 for edit and NewSaathratriEntity3FormGroupInput for create.
 */
type SaathratriEntity3FormGroupInput = ISaathratriEntity3 | PartialWithRequiredKeyOf<NewSaathratriEntity3>;

type SaathratriEntity3FormDefaults = Pick<NewSaathratriEntity3, 'compositeId'>;

type SaathratriEntity3FormGroupContent = {
  compositeId: FormGroup<{
    entityType: FormControl<ISaathratriEntity3['compositeId']['entityType']>;
    createdTimeId: FormControl<ISaathratriEntity3['compositeId']['createdTimeId']>;
  }>;
  entityName: FormControl<ISaathratriEntity3['entityName']>;
  entityDescription: FormControl<ISaathratriEntity3['entityDescription']>;
  entityCost: FormControl<ISaathratriEntity3['entityCost']>;
  departureDate: FormControl<ISaathratriEntity3['departureDate']>;
  tags: FormControl<ISaathratriEntity3['tags']>;
};

export type SaathratriEntity3FormGroup = FormGroup<SaathratriEntity3FormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SaathratriEntity3FormService {
  createSaathratriEntity3FormGroup(
    saathratriEntity3: SaathratriEntity3FormGroupInput = { compositeId: { entityType: null, createdTimeId: null } },
  ): SaathratriEntity3FormGroup {
    const saathratriEntity3RawValue = {
      ...this.getFormDefaults(),
      ...saathratriEntity3,
    };
    return new FormGroup<SaathratriEntity3FormGroupContent>({
      compositeId: new FormGroup({
        entityType: new FormControl(
          { value: saathratriEntity3RawValue.compositeId.entityType, disabled: saathratriEntity3RawValue.compositeId.entityType !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        createdTimeId: new FormControl(
          {
            value: saathratriEntity3RawValue.compositeId.createdTimeId,
            disabled: saathratriEntity3RawValue.compositeId.createdTimeId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      entityName: new FormControl(saathratriEntity3RawValue.entityName),
      entityDescription: new FormControl(saathratriEntity3RawValue.entityDescription),
      entityCost: new FormControl(saathratriEntity3RawValue.entityCost),
      departureDate: new FormControl(saathratriEntity3RawValue.departureDate),
      tags: new FormControl(saathratriEntity3RawValue.tags),
    });
  }

  getSaathratriEntity3(form: SaathratriEntity3FormGroup): ISaathratriEntity3 | NewSaathratriEntity3 {
    return form.getRawValue() as ISaathratriEntity3 | NewSaathratriEntity3;
  }

  resetForm(form: SaathratriEntity3FormGroup, saathratriEntity3: SaathratriEntity3FormGroupInput): void {
    const saathratriEntity3RawValue = { ...this.getFormDefaults(), ...saathratriEntity3 };
    form.reset(
      {
        ...saathratriEntity3RawValue,
        compositeId: {
          entityType: { value: saathratriEntity3RawValue.compositeId.entityType, disabled: true },
          createdTimeId: { value: saathratriEntity3RawValue.compositeId.createdTimeId, disabled: true },
        },
        departureDate: saathratriEntity3RawValue.departureDate?.toDate(),
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SaathratriEntity3FormDefaults {
    return {
      compositeId: {
        entityType: null,
        createdTimeId: null,
      },
    };
  }
}
