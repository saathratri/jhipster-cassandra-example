import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../add-ons-available-by-organization.test-samples';

import { AddOnsAvailableByOrganizationFormService } from './add-ons-available-by-organization-form.service';

describe('AddOnsAvailableByOrganization Form Service', () => {
  let service: AddOnsAvailableByOrganizationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddOnsAvailableByOrganizationFormService);
  });

  describe('Service methods', () => {
    describe('createAddOnsAvailableByOrganizationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAddOnsAvailableByOrganizationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            entityType: expect.any(Object),
            entityId: expect.any(Object),
            addOnId: expect.any(Object),
            addOnType: expect.any(Object),
            addOnDetailsText: expect.any(Object),
            addOnDetailsDecimal: expect.any(Object),
            addOnDetailsBoolean: expect.any(Object),
            addOnDetailsBigInt: expect.any(Object),
          }),
        );
      });

      it('passing IAddOnsAvailableByOrganization should create a new form with FormGroup', () => {
        const formGroup = service.createAddOnsAvailableByOrganizationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            entityType: expect.any(Object),
            entityId: expect.any(Object),
            addOnId: expect.any(Object),
            addOnType: expect.any(Object),
            addOnDetailsText: expect.any(Object),
            addOnDetailsDecimal: expect.any(Object),
            addOnDetailsBoolean: expect.any(Object),
            addOnDetailsBigInt: expect.any(Object),
          }),
        );
      });
    });

    describe('getAddOnsAvailableByOrganization', () => {
      it('should return NewAddOnsAvailableByOrganization for default AddOnsAvailableByOrganization initial value', () => {
        const formGroup = service.createAddOnsAvailableByOrganizationFormGroup(sampleWithNewData);

        const addOnsAvailableByOrganization = service.getAddOnsAvailableByOrganization(formGroup) as any;

        expect(addOnsAvailableByOrganization).toMatchObject(sampleWithNewData);
      });

      it('should return NewAddOnsAvailableByOrganization for empty AddOnsAvailableByOrganization initial value', () => {
        const formGroup = service.createAddOnsAvailableByOrganizationFormGroup();

        const addOnsAvailableByOrganization = service.getAddOnsAvailableByOrganization(formGroup) as any;

        expect(addOnsAvailableByOrganization).toMatchObject({});
      });

      it('should return IAddOnsAvailableByOrganization', () => {
        const formGroup = service.createAddOnsAvailableByOrganizationFormGroup(sampleWithRequiredData);

        const addOnsAvailableByOrganization = service.getAddOnsAvailableByOrganization(formGroup) as any;

        expect(addOnsAvailableByOrganization).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAddOnsAvailableByOrganization should not enable organizationId FormControl', () => {
        const formGroup = service.createAddOnsAvailableByOrganizationFormGroup();
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });

      it('passing NewAddOnsAvailableByOrganization should disable organizationId FormControl', () => {
        const formGroup = service.createAddOnsAvailableByOrganizationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, { organizationId: null });

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
