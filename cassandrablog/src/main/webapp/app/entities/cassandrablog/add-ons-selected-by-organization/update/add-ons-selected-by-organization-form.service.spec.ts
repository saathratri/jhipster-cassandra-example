import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../add-ons-selected-by-organization.test-samples';

import { AddOnsSelectedByOrganizationFormService } from './add-ons-selected-by-organization-form.service';

describe('AddOnsSelectedByOrganization Form Service', () => {
  let service: AddOnsSelectedByOrganizationFormService;

  beforeEach(() => {
    service = TestBed.inject(AddOnsSelectedByOrganizationFormService);
  });

  describe('Service methods', () => {
    describe('createAddOnsSelectedByOrganizationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAddOnsSelectedByOrganizationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            departureDate: expect.any(Object),
            customerId: expect.any(Object),
            customerFirstName: expect.any(Object),
            customerLastName: expect.any(Object),
            customerUpdatedEmail: expect.any(Object),
            customerUpdatedPhoneNumber: expect.any(Object),
            customerEstimatedArrivalTime: expect.any(Object),
            tinyUrlShortCode: expect.any(Object),
            addOnDetailsText: expect.any(Object),
            addOnDetailsDecimal: expect.any(Object),
            addOnDetailsBoolean: expect.any(Object),
            addOnDetailsBigInt: expect.any(Object),
          }),
        );
      });

      it('passing IAddOnsSelectedByOrganization should create a new form with FormGroup', () => {
        const formGroup = service.createAddOnsSelectedByOrganizationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            departureDate: expect.any(Object),
            customerId: expect.any(Object),
            customerFirstName: expect.any(Object),
            customerLastName: expect.any(Object),
            customerUpdatedEmail: expect.any(Object),
            customerUpdatedPhoneNumber: expect.any(Object),
            customerEstimatedArrivalTime: expect.any(Object),
            tinyUrlShortCode: expect.any(Object),
            addOnDetailsText: expect.any(Object),
            addOnDetailsDecimal: expect.any(Object),
            addOnDetailsBoolean: expect.any(Object),
            addOnDetailsBigInt: expect.any(Object),
          }),
        );
      });
    });

    describe('getAddOnsSelectedByOrganization', () => {
      it('should return NewAddOnsSelectedByOrganization for default AddOnsSelectedByOrganization initial value', () => {
        const formGroup = service.createAddOnsSelectedByOrganizationFormGroup(sampleWithNewData);

        const addOnsSelectedByOrganization = service.getAddOnsSelectedByOrganization(formGroup);

        expect(addOnsSelectedByOrganization).toMatchObject(sampleWithNewData);
      });

      it('should return NewAddOnsSelectedByOrganization for empty AddOnsSelectedByOrganization initial value', () => {
        const formGroup = service.createAddOnsSelectedByOrganizationFormGroup();

        const addOnsSelectedByOrganization = service.getAddOnsSelectedByOrganization(formGroup);

        expect(addOnsSelectedByOrganization).toMatchObject({});
      });

      it('should return IAddOnsSelectedByOrganization', () => {
        const formGroup = service.createAddOnsSelectedByOrganizationFormGroup(sampleWithRequiredData);

        const addOnsSelectedByOrganization = service.getAddOnsSelectedByOrganization(formGroup);

        expect(addOnsSelectedByOrganization).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAddOnsSelectedByOrganization should keep the key control disabled', () => {
        const formGroup = service.createAddOnsSelectedByOrganizationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createAddOnsSelectedByOrganizationFormGroup();

        service.resetForm(formGroup, {
          compositeId: { organizationId: null, arrivalDate: null, accountNumber: null, createdTimeId: null },
        });

        expect(formGroup.controls.compositeId.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
