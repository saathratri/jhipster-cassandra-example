import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../set-entity-by-organization.test-samples';

import { SetEntityByOrganizationFormService } from './set-entity-by-organization-form.service';

describe('SetEntityByOrganization Form Service', () => {
  let service: SetEntityByOrganizationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SetEntityByOrganizationFormService);
  });

  describe('Service methods', () => {
    describe('createSetEntityByOrganizationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSetEntityByOrganizationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            tags: expect.any(Object),
          }),
        );
      });

      it('passing ISetEntityByOrganization should create a new form with FormGroup', () => {
        const formGroup = service.createSetEntityByOrganizationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            tags: expect.any(Object),
          }),
        );
      });
    });

    describe('getSetEntityByOrganization', () => {
      it('should return NewSetEntityByOrganization for default SetEntityByOrganization initial value', () => {
        const formGroup = service.createSetEntityByOrganizationFormGroup(sampleWithNewData);

        const setEntityByOrganization = service.getSetEntityByOrganization(formGroup) as any;

        expect(setEntityByOrganization).toMatchObject(sampleWithNewData);
      });

      it('should return NewSetEntityByOrganization for empty SetEntityByOrganization initial value', () => {
        const formGroup = service.createSetEntityByOrganizationFormGroup();

        const setEntityByOrganization = service.getSetEntityByOrganization(formGroup) as any;

        expect(setEntityByOrganization).toMatchObject({});
      });

      it('should return ISetEntityByOrganization', () => {
        const formGroup = service.createSetEntityByOrganizationFormGroup(sampleWithRequiredData);

        const setEntityByOrganization = service.getSetEntityByOrganization(formGroup) as any;

        expect(setEntityByOrganization).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISetEntityByOrganization should not enable organizationId FormControl', () => {
        const formGroup = service.createSetEntityByOrganizationFormGroup();
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });

      it('passing NewSetEntityByOrganization should disable organizationId FormControl', () => {
        const formGroup = service.createSetEntityByOrganizationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, { organizationId: null });

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
