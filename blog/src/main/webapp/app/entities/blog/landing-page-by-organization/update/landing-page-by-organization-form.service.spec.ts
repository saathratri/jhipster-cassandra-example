import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../landing-page-by-organization.test-samples';

import { LandingPageByOrganizationFormService } from './landing-page-by-organization-form.service';

describe('LandingPageByOrganization Form Service', () => {
  let service: LandingPageByOrganizationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LandingPageByOrganizationFormService);
  });

  describe('Service methods', () => {
    describe('createLandingPageByOrganizationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLandingPageByOrganizationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            detailsText: expect.any(Object),
            detailsDecimal: expect.any(Object),
            detailsBoolean: expect.any(Object),
            detailsBigInt: expect.any(Object),
          }),
        );
      });

      it('passing ILandingPageByOrganization should create a new form with FormGroup', () => {
        const formGroup = service.createLandingPageByOrganizationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            detailsText: expect.any(Object),
            detailsDecimal: expect.any(Object),
            detailsBoolean: expect.any(Object),
            detailsBigInt: expect.any(Object),
          }),
        );
      });
    });

    describe('getLandingPageByOrganization', () => {
      it('should return NewLandingPageByOrganization for default LandingPageByOrganization initial value', () => {
        const formGroup = service.createLandingPageByOrganizationFormGroup(sampleWithNewData);

        const landingPageByOrganization = service.getLandingPageByOrganization(formGroup) as any;

        expect(landingPageByOrganization).toMatchObject(sampleWithNewData);
      });

      it('should return NewLandingPageByOrganization for empty LandingPageByOrganization initial value', () => {
        const formGroup = service.createLandingPageByOrganizationFormGroup();

        const landingPageByOrganization = service.getLandingPageByOrganization(formGroup) as any;

        expect(landingPageByOrganization).toMatchObject({});
      });

      it('should return ILandingPageByOrganization', () => {
        const formGroup = service.createLandingPageByOrganizationFormGroup(sampleWithRequiredData);

        const landingPageByOrganization = service.getLandingPageByOrganization(formGroup) as any;

        expect(landingPageByOrganization).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILandingPageByOrganization should not enable organizationId FormControl', () => {
        const formGroup = service.createLandingPageByOrganizationFormGroup();
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });

      it('passing NewLandingPageByOrganization should disable organizationId FormControl', () => {
        const formGroup = service.createLandingPageByOrganizationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, { organizationId: null });

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
