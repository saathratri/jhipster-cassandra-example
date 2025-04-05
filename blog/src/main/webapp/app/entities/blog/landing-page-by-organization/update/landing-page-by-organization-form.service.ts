import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILandingPageByOrganization, NewLandingPageByOrganization } from '../landing-page-by-organization.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { organizationId: unknown }> = Partial<Omit<T, 'organizationId'>> & {
  organizationId: T['organizationId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILandingPageByOrganization for edit and NewLandingPageByOrganizationFormGroupInput for create.
 */
type LandingPageByOrganizationFormGroupInput = ILandingPageByOrganization | PartialWithRequiredKeyOf<NewLandingPageByOrganization>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILandingPageByOrganization | NewLandingPageByOrganization> = Omit<T, 'detailsBigInt'> & {
  detailsBigInt: Record<string, string> | null | undefined;
};

type LandingPageByOrganizationFormRawValue = FormValueOf<ILandingPageByOrganization>;

type NewLandingPageByOrganizationFormRawValue = FormValueOf<NewLandingPageByOrganization>;

type LandingPageByOrganizationFormDefaults = Pick<NewLandingPageByOrganization, 'organizationId' | 'detailsBoolean' | 'detailsBigInt'>;

type LandingPageByOrganizationFormGroupContent = {
  organizationId: FormControl<LandingPageByOrganizationFormRawValue['organizationId']>;
  detailsText: FormControl<LandingPageByOrganizationFormRawValue['detailsText']>;
  detailsDecimal: FormControl<LandingPageByOrganizationFormRawValue['detailsDecimal']>;
  detailsBoolean: FormControl<LandingPageByOrganizationFormRawValue['detailsBoolean']>;
  detailsBigInt: FormControl<LandingPageByOrganizationFormRawValue['detailsBigInt']>;
};

export type LandingPageByOrganizationFormGroup = FormGroup<LandingPageByOrganizationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LandingPageByOrganizationFormService {
  createLandingPageByOrganizationFormGroup(
    landingPageByOrganization: LandingPageByOrganizationFormGroupInput = { organizationId: '' },
  ): LandingPageByOrganizationFormGroup {
    const landingPageByOrganizationRawValue = this.convertLandingPageByOrganizationToLandingPageByOrganizationRawValue({
      ...this.getFormDefaults(),
      ...landingPageByOrganization,
    });
    return new FormGroup<LandingPageByOrganizationFormGroupContent>({
      organizationId: new FormControl(
        { value: landingPageByOrganizationRawValue.organizationId, disabled: landingPageByOrganizationRawValue.organizationId !== '' },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      detailsText: new FormControl(landingPageByOrganizationRawValue.detailsText),
      detailsDecimal: new FormControl(landingPageByOrganizationRawValue.detailsDecimal),
      detailsBoolean: new FormControl(landingPageByOrganizationRawValue.detailsBoolean),
      detailsBigInt: new FormControl(landingPageByOrganizationRawValue.detailsBigInt),
    });
  }

  getLandingPageByOrganization(form: LandingPageByOrganizationFormGroup): ILandingPageByOrganization | NewLandingPageByOrganization {
    return this.convertLandingPageByOrganizationRawValueToLandingPageByOrganization(
      form.getRawValue() as LandingPageByOrganizationFormRawValue | NewLandingPageByOrganizationFormRawValue,
    );
  }

  resetForm(form: LandingPageByOrganizationFormGroup, landingPageByOrganization: LandingPageByOrganizationFormGroupInput): void {
    const landingPageByOrganizationRawValue = this.convertLandingPageByOrganizationToLandingPageByOrganizationRawValue({
      ...this.getFormDefaults(),
      ...landingPageByOrganization,
    });
    form.reset(
      {
        ...landingPageByOrganizationRawValue,
        organizationId: { value: landingPageByOrganizationRawValue.organizationId, disabled: true },
        detailsBigInt: landingPageByOrganizationRawValue.detailsBigInt,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LandingPageByOrganizationFormDefaults {
    return {
      organizationId: '',
    };
  }

  private convertLandingPageByOrganizationRawValueToLandingPageByOrganization(
    rawLandingPageByOrganization: LandingPageByOrganizationFormRawValue | NewLandingPageByOrganizationFormRawValue,
  ): ILandingPageByOrganization | NewLandingPageByOrganization {
    return {
      ...rawLandingPageByOrganization,
      detailsBigInt: rawLandingPageByOrganization.detailsBigInt
        ? Object.fromEntries(
            Object.entries(rawLandingPageByOrganization.detailsBigInt).map(([key, value]) => [key, dayjs(value, DATE_TIME_FORMAT)]),
          )
        : {},
    };
  }

  private convertLandingPageByOrganizationToLandingPageByOrganizationRawValue(
    landingPageByOrganization: ILandingPageByOrganization | (Partial<NewLandingPageByOrganization> & LandingPageByOrganizationFormDefaults),
  ): LandingPageByOrganizationFormRawValue | PartialWithRequiredKeyOf<NewLandingPageByOrganizationFormRawValue> {
    return {
      ...landingPageByOrganization,
      detailsBigInt: landingPageByOrganization.detailsBigInt
        ? Object.fromEntries(
            Object.entries(landingPageByOrganization.detailsBigInt).map(([key, value]) => [key, value.format(DATE_TIME_FORMAT)]),
          )
        : {},
    };
  }
}
