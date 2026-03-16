import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISetEntityByOrganization, NewSetEntityByOrganization } from '../set-entity-by-organization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { organizationId: unknown }> = Partial<Omit<T, 'organizationId'>> & {
  organizationId: T['organizationId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISetEntityByOrganization for edit and NewSetEntityByOrganizationFormGroupInput for create.
 */
type SetEntityByOrganizationFormGroupInput = ISetEntityByOrganization | PartialWithRequiredKeyOf<NewSetEntityByOrganization>;

type SetEntityByOrganizationFormDefaults = Pick<NewSetEntityByOrganization, 'organizationId'>;

type SetEntityByOrganizationFormGroupContent = {
  organizationId: FormControl<ISetEntityByOrganization['organizationId']>;
  tags: FormControl<ISetEntityByOrganization['tags']>;
};

export type SetEntityByOrganizationFormGroup = FormGroup<SetEntityByOrganizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SetEntityByOrganizationFormService {
  createSetEntityByOrganizationFormGroup(
    setEntityByOrganization: SetEntityByOrganizationFormGroupInput = { organizationId: '' },
  ): SetEntityByOrganizationFormGroup {
    const setEntityByOrganizationRawValue = {
      ...this.getFormDefaults(),
      ...setEntityByOrganization,
    };
    return new FormGroup<SetEntityByOrganizationFormGroupContent>({
      organizationId: new FormControl(
        { value: setEntityByOrganizationRawValue.organizationId, disabled: setEntityByOrganizationRawValue.organizationId !== '' },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tags: new FormControl(setEntityByOrganizationRawValue.tags),
    });
  }

  getSetEntityByOrganization(form: SetEntityByOrganizationFormGroup): ISetEntityByOrganization | NewSetEntityByOrganization {
    return form.getRawValue() as ISetEntityByOrganization | NewSetEntityByOrganization;
  }

  resetForm(form: SetEntityByOrganizationFormGroup, setEntityByOrganization: SetEntityByOrganizationFormGroupInput): void {
    const setEntityByOrganizationRawValue = { ...this.getFormDefaults(), ...setEntityByOrganization };
    form.reset(
      {
        ...setEntityByOrganizationRawValue,
        organizationId: { value: setEntityByOrganizationRawValue.organizationId, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SetEntityByOrganizationFormDefaults {
    return {
      organizationId: '',
    };
  }
}
