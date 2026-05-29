import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../add-ons-available-by-organization.test-samples';

import { AddOnsAvailableByOrganizationFormService } from './add-ons-available-by-organization-form.service';

describe('AddOnsAvailableByOrganization Form Service', () => {
  let service: AddOnsAvailableByOrganizationFormService;

  beforeEach(() => {
    service = TestBed.inject(AddOnsAvailableByOrganizationFormService);
  });

  describe('Service methods', () => {
    describe('createAddOnsAvailableByOrganizationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAddOnsAvailableByOrganizationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
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
            compositeId: expect.any(Object),
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

        const addOnsAvailableByOrganization = service.getAddOnsAvailableByOrganization(formGroup);

        expect(addOnsAvailableByOrganization).toMatchObject(sampleWithNewData);
      });

      it('should return NewAddOnsAvailableByOrganization for empty AddOnsAvailableByOrganization initial value', () => {
        const formGroup = service.createAddOnsAvailableByOrganizationFormGroup();

        const addOnsAvailableByOrganization = service.getAddOnsAvailableByOrganization(formGroup);

        expect(addOnsAvailableByOrganization).toMatchObject({});
      });

      it('should return IAddOnsAvailableByOrganization', () => {
        const formGroup = service.createAddOnsAvailableByOrganizationFormGroup(sampleWithRequiredData);

        const addOnsAvailableByOrganization = service.getAddOnsAvailableByOrganization(formGroup);

        expect(addOnsAvailableByOrganization).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAddOnsAvailableByOrganization should keep the key control disabled', () => {
        const formGroup = service.createAddOnsAvailableByOrganizationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createAddOnsAvailableByOrganizationFormGroup();

        service.resetForm(formGroup, { compositeId: { organizationId: null, entityType: null, entityId: null, addOnId: null } });

        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
