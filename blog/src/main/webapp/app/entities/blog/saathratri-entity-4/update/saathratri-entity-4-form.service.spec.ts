import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../saathratri-entity-4.test-samples';

import { SaathratriEntity4FormService } from './saathratri-entity-4-form.service';

describe('SaathratriEntity4 Form Service', () => {
  let service: SaathratriEntity4FormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaathratriEntity4FormService);
  });

  describe('Service methods', () => {
    describe('createSaathratriEntity4FormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSaathratriEntity4FormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            attributeKey: expect.any(Object),
            attributeValue: expect.any(Object),
          }),
        );
      });

      it('passing ISaathratriEntity4 should create a new form with FormGroup', () => {
        const formGroup = service.createSaathratriEntity4FormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            attributeKey: expect.any(Object),
            attributeValue: expect.any(Object),
          }),
        );
      });
    });

    describe('getSaathratriEntity4', () => {
      it('should return NewSaathratriEntity4 for default SaathratriEntity4 initial value', () => {
        const formGroup = service.createSaathratriEntity4FormGroup(sampleWithNewData);

        const saathratriEntity4 = service.getSaathratriEntity4(formGroup) as any;

        expect(saathratriEntity4).toMatchObject(sampleWithNewData);
      });

      it('should return NewSaathratriEntity4 for empty SaathratriEntity4 initial value', () => {
        const formGroup = service.createSaathratriEntity4FormGroup();

        const saathratriEntity4 = service.getSaathratriEntity4(formGroup) as any;

        expect(saathratriEntity4).toMatchObject({});
      });

      it('should return ISaathratriEntity4', () => {
        const formGroup = service.createSaathratriEntity4FormGroup(sampleWithRequiredData);

        const saathratriEntity4 = service.getSaathratriEntity4(formGroup) as any;

        expect(saathratriEntity4).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISaathratriEntity4 should not enable organizationId FormControl', () => {
        const formGroup = service.createSaathratriEntity4FormGroup();
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });

      it('passing NewSaathratriEntity4 should disable organizationId FormControl', () => {
        const formGroup = service.createSaathratriEntity4FormGroup(sampleWithRequiredData);
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, { organizationId: null });

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
