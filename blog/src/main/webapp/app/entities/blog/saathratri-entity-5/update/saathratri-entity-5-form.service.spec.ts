import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../saathratri-entity-5.test-samples';

import { SaathratriEntity5FormService } from './saathratri-entity-5-form.service';

describe('SaathratriEntity5 Form Service', () => {
  let service: SaathratriEntity5FormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaathratriEntity5FormService);
  });

  describe('Service methods', () => {
    describe('createSaathratriEntity5FormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSaathratriEntity5FormGroup();

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

      it('passing ISaathratriEntity5 should create a new form with FormGroup', () => {
        const formGroup = service.createSaathratriEntity5FormGroup(sampleWithRequiredData);

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

    describe('getSaathratriEntity5', () => {
      it('should return NewSaathratriEntity5 for default SaathratriEntity5 initial value', () => {
        const formGroup = service.createSaathratriEntity5FormGroup(sampleWithNewData);

        const saathratriEntity5 = service.getSaathratriEntity5(formGroup) as any;

        expect(saathratriEntity5).toMatchObject(sampleWithNewData);
      });

      it('should return NewSaathratriEntity5 for empty SaathratriEntity5 initial value', () => {
        const formGroup = service.createSaathratriEntity5FormGroup();

        const saathratriEntity5 = service.getSaathratriEntity5(formGroup) as any;

        expect(saathratriEntity5).toMatchObject({});
      });

      it('should return ISaathratriEntity5', () => {
        const formGroup = service.createSaathratriEntity5FormGroup(sampleWithRequiredData);

        const saathratriEntity5 = service.getSaathratriEntity5(formGroup) as any;

        expect(saathratriEntity5).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISaathratriEntity5 should not enable organizationId FormControl', () => {
        const formGroup = service.createSaathratriEntity5FormGroup();
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });

      it('passing NewSaathratriEntity5 should disable organizationId FormControl', () => {
        const formGroup = service.createSaathratriEntity5FormGroup(sampleWithRequiredData);
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, { organizationId: null });

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
