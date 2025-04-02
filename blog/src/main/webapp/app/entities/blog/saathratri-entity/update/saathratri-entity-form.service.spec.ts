import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../saathratri-entity.test-samples';

import { SaathratriEntityFormService } from './saathratri-entity-form.service';

describe('SaathratriEntity Form Service', () => {
  let service: SaathratriEntityFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaathratriEntityFormService);
  });

  describe('Service methods', () => {
    describe('createSaathratriEntityFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSaathratriEntityFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            entityId: expect.any(Object),
            entityName: expect.any(Object),
            entityDescription: expect.any(Object),
            entityCost: expect.any(Object),
            createdId: expect.any(Object),
            createdTimeId: expect.any(Object),
          }),
        );
      });

      it('passing ISaathratriEntity should create a new form with FormGroup', () => {
        const formGroup = service.createSaathratriEntityFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            entityId: expect.any(Object),
            entityName: expect.any(Object),
            entityDescription: expect.any(Object),
            entityCost: expect.any(Object),
            createdId: expect.any(Object),
            createdTimeId: expect.any(Object),
          }),
        );
      });
    });

    describe('getSaathratriEntity', () => {
      it('should return NewSaathratriEntity for default SaathratriEntity initial value', () => {
        const formGroup = service.createSaathratriEntityFormGroup(sampleWithNewData);

        const saathratriEntity = service.getSaathratriEntity(formGroup) as any;

        expect(saathratriEntity).toMatchObject(sampleWithNewData);
      });

      it('should return NewSaathratriEntity for empty SaathratriEntity initial value', () => {
        const formGroup = service.createSaathratriEntityFormGroup();

        const saathratriEntity = service.getSaathratriEntity(formGroup) as any;

        expect(saathratriEntity).toMatchObject({});
      });

      it('should return ISaathratriEntity', () => {
        const formGroup = service.createSaathratriEntityFormGroup(sampleWithRequiredData);

        const saathratriEntity = service.getSaathratriEntity(formGroup) as any;

        expect(saathratriEntity).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISaathratriEntity should not enable entityId FormControl', () => {
        const formGroup = service.createSaathratriEntityFormGroup();
        expect(formGroup.controls.entityId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.entityId.disabled).toBe(true);
      });

      it('passing NewSaathratriEntity should disable entityId FormControl', () => {
        const formGroup = service.createSaathratriEntityFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.entityId.disabled).toBe(true);

        service.resetForm(formGroup, { entityId: null });

        expect(formGroup.controls.entityId.disabled).toBe(true);
      });
    });
  });
});
