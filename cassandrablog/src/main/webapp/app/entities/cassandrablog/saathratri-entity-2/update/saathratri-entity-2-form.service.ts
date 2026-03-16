import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISaathratriEntity2, NewSaathratriEntity2 } from '../saathratri-entity-2.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<
  T extends { compositeId: { entityTypeId: unknown; yearOfDateAdded: unknown; arrivalDate: unknown; blogId: unknown } },
> = Partial<Omit<T, 'compositeId'>> & { compositeId: T['compositeId'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISaathratriEntity2 for edit and NewSaathratriEntity2FormGroupInput for create.
 */
type SaathratriEntity2FormGroupInput = ISaathratriEntity2 | PartialWithRequiredKeyOf<NewSaathratriEntity2>;

type SaathratriEntity2FormDefaults = Pick<NewSaathratriEntity2, 'compositeId'>;

type SaathratriEntity2FormGroupContent = {
  compositeId: FormGroup<{
    entityTypeId: FormControl<ISaathratriEntity2['compositeId']['entityTypeId']>;
    yearOfDateAdded: FormControl<ISaathratriEntity2['compositeId']['yearOfDateAdded']>;
    arrivalDate: FormControl<ISaathratriEntity2['compositeId']['arrivalDate']>;
    blogId: FormControl<ISaathratriEntity2['compositeId']['blogId']>;
  }>;
  entityName: FormControl<ISaathratriEntity2['entityName']>;
  entityDescription: FormControl<ISaathratriEntity2['entityDescription']>;
  entityCost: FormControl<ISaathratriEntity2['entityCost']>;
  departureDate: FormControl<ISaathratriEntity2['departureDate']>;
};

export type SaathratriEntity2FormGroup = FormGroup<SaathratriEntity2FormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SaathratriEntity2FormService {
  createSaathratriEntity2FormGroup(
    saathratriEntity2: SaathratriEntity2FormGroupInput = {
      compositeId: { entityTypeId: null, yearOfDateAdded: null, arrivalDate: null, blogId: null },
    },
  ): SaathratriEntity2FormGroup {
    const saathratriEntity2RawValue = {
      ...this.getFormDefaults(),
      ...saathratriEntity2,
    };
    return new FormGroup<SaathratriEntity2FormGroupContent>({
      compositeId: new FormGroup({
        entityTypeId: new FormControl(
          {
            value: saathratriEntity2RawValue.compositeId.entityTypeId,
            disabled: saathratriEntity2RawValue.compositeId.entityTypeId !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        yearOfDateAdded: new FormControl(
          {
            value: saathratriEntity2RawValue.compositeId.yearOfDateAdded,
            disabled: saathratriEntity2RawValue.compositeId.yearOfDateAdded !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        arrivalDate: new FormControl(
          {
            value: saathratriEntity2RawValue.compositeId.arrivalDate,
            disabled: saathratriEntity2RawValue.compositeId.arrivalDate !== null,
          },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        blogId: new FormControl(
          { value: saathratriEntity2RawValue.compositeId.blogId, disabled: saathratriEntity2RawValue.compositeId.blogId !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      entityName: new FormControl(saathratriEntity2RawValue.entityName),
      entityDescription: new FormControl(saathratriEntity2RawValue.entityDescription),
      entityCost: new FormControl(saathratriEntity2RawValue.entityCost),
      departureDate: new FormControl(saathratriEntity2RawValue.departureDate),
    });
  }

  getSaathratriEntity2(form: SaathratriEntity2FormGroup): ISaathratriEntity2 | NewSaathratriEntity2 {
    return form.getRawValue() as ISaathratriEntity2 | NewSaathratriEntity2;
  }

  resetForm(form: SaathratriEntity2FormGroup, saathratriEntity2: SaathratriEntity2FormGroupInput): void {
    const saathratriEntity2RawValue = { ...this.getFormDefaults(), ...saathratriEntity2 };
    form.reset(
      {
        ...saathratriEntity2RawValue,
        compositeId: {
          entityTypeId: { value: saathratriEntity2RawValue.compositeId.entityTypeId, disabled: true },
          yearOfDateAdded: { value: saathratriEntity2RawValue.compositeId.yearOfDateAdded, disabled: true },
          arrivalDate: { value: saathratriEntity2RawValue.compositeId.arrivalDate?.toDate(), disabled: true },
          blogId: { value: saathratriEntity2RawValue.compositeId.blogId, disabled: true },
        },
        departureDate: saathratriEntity2RawValue.departureDate?.toDate(),
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SaathratriEntity2FormDefaults {
    return {
      compositeId: {
        entityTypeId: null,
        yearOfDateAdded: null,
        arrivalDate: null,
        blogId: null,
      },
    };
  }
}
