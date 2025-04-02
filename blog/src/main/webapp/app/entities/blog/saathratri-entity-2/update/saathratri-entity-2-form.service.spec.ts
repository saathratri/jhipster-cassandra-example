import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../saathratri-entity-2.test-samples';

import { SaathratriEntity2FormService } from './saathratri-entity-2-form.service';

describe('SaathratriEntity2 Form Service', () => {
  let service: SaathratriEntity2FormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaathratriEntity2FormService);
  });

  describe('Service methods', () => {
    describe('createSaathratriEntity2FormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSaathratriEntity2FormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            entityTypeId: expect.any(Object),
            yearOfDateAdded: expect.any(Object),
            arrivalDate: expect.any(Object),
            blogId: expect.any(Object),
            entityName: expect.any(Object),
            entityDescription: expect.any(Object),
            entityCost: expect.any(Object),
            departureDate: expect.any(Object),
          }),
        );
      });

      it('passing ISaathratriEntity2 should create a new form with FormGroup', () => {
        const formGroup = service.createSaathratriEntity2FormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            entityTypeId: expect.any(Object),
            yearOfDateAdded: expect.any(Object),
            arrivalDate: expect.any(Object),
            blogId: expect.any(Object),
            entityName: expect.any(Object),
            entityDescription: expect.any(Object),
            entityCost: expect.any(Object),
            departureDate: expect.any(Object),
          }),
        );
      });
    });

    describe('getSaathratriEntity2', () => {
      it('should return NewSaathratriEntity2 for default SaathratriEntity2 initial value', () => {
        const formGroup = service.createSaathratriEntity2FormGroup(sampleWithNewData);

        const saathratriEntity2 = service.getSaathratriEntity2(formGroup) as any;

        expect(saathratriEntity2).toMatchObject(sampleWithNewData);
      });

      it('should return NewSaathratriEntity2 for empty SaathratriEntity2 initial value', () => {
        const formGroup = service.createSaathratriEntity2FormGroup();

        const saathratriEntity2 = service.getSaathratriEntity2(formGroup) as any;

        expect(saathratriEntity2).toMatchObject({});
      });

      it('should return ISaathratriEntity2', () => {
        const formGroup = service.createSaathratriEntity2FormGroup(sampleWithRequiredData);

        const saathratriEntity2 = service.getSaathratriEntity2(formGroup) as any;

        expect(saathratriEntity2).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISaathratriEntity2 should not enable entityTypeId FormControl', () => {
        const formGroup = service.createSaathratriEntity2FormGroup();
        expect(formGroup.controls.entityTypeId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.entityTypeId.disabled).toBe(true);
      });

      it('passing NewSaathratriEntity2 should disable entityTypeId FormControl', () => {
        const formGroup = service.createSaathratriEntity2FormGroup(sampleWithRequiredData);
        expect(formGroup.controls.entityTypeId.disabled).toBe(true);

        service.resetForm(formGroup, { entityTypeId: null });

        expect(formGroup.controls.entityTypeId.disabled).toBe(true);
      });
    });
  });
});
