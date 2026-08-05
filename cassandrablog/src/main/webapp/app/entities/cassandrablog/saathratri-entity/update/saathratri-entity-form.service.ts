import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISaathratriEntity, NewSaathratriEntity } from '../saathratri-entity.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { entityId: unknown }> = Partial<Omit<T, 'entityId'>> & { entityId: T['entityId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISaathratriEntity for edit and NewSaathratriEntityFormGroupInput for create.
 */
type SaathratriEntityFormGroupInput = ISaathratriEntity | PartialWithRequiredKeyOf<NewSaathratriEntity>;

type SaathratriEntityFormDefaults = Pick<NewSaathratriEntity, 'entityId'>;

type SaathratriEntityFormGroupContent = {
  entityId: FormControl<ISaathratriEntity['entityId']>;
  entityName: FormControl<ISaathratriEntity['entityName']>;
  entityDescription: FormControl<ISaathratriEntity['entityDescription']>;
  entityCost: FormControl<ISaathratriEntity['entityCost']>;
  createdId: FormControl<ISaathratriEntity['createdId']>;
  createdTimeId: FormControl<ISaathratriEntity['createdTimeId']>;
};

export type SaathratriEntityFormGroup = FormGroup<SaathratriEntityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SaathratriEntityFormService {
  createSaathratriEntityFormGroup(saathratriEntity: SaathratriEntityFormGroupInput = { entityId: '' }): SaathratriEntityFormGroup {
    const saathratriEntityRawValue = {
      ...this.getFormDefaults(),
      ...saathratriEntity,
    };
    return new FormGroup<SaathratriEntityFormGroupContent>({
      entityId: new FormControl(
        { value: saathratriEntityRawValue.entityId, disabled: saathratriEntityRawValue.entityId !== '' },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      entityName: new FormControl(saathratriEntityRawValue.entityName),
      entityDescription: new FormControl(saathratriEntityRawValue.entityDescription),
      entityCost: new FormControl(saathratriEntityRawValue.entityCost),
      createdId: new FormControl(saathratriEntityRawValue.createdId),
      createdTimeId: new FormControl(saathratriEntityRawValue.createdTimeId),
    });
  }

  getSaathratriEntity(form: SaathratriEntityFormGroup): ISaathratriEntity | NewSaathratriEntity {
    return form.getRawValue() as ISaathratriEntity | NewSaathratriEntity;
  }

  resetForm(form: SaathratriEntityFormGroup, saathratriEntity: SaathratriEntityFormGroupInput): void {
    const saathratriEntityRawValue = { ...this.getFormDefaults(), ...saathratriEntity };
    form.reset(
      {
        ...saathratriEntityRawValue,
        entityId: { value: saathratriEntityRawValue.entityId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SaathratriEntityFormDefaults {
    return {
      entityId: '',
    };
  }
}
