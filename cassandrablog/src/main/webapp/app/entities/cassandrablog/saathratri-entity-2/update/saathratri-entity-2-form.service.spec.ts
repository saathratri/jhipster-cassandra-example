import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../saathratri-entity-2.test-samples';

import { SaathratriEntity2FormService } from './saathratri-entity-2-form.service';

describe('SaathratriEntity2 Form Service', () => {
  let service: SaathratriEntity2FormService;

  beforeEach(() => {
    service = TestBed.inject(SaathratriEntity2FormService);
  });

  describe('Service methods', () => {
    describe('createSaathratriEntity2FormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSaathratriEntity2FormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
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
            compositeId: expect.any(Object),
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

        const saathratriEntity2 = service.getSaathratriEntity2(formGroup);

        expect(saathratriEntity2).toMatchObject(sampleWithNewData);
      });

      it('should return NewSaathratriEntity2 for empty SaathratriEntity2 initial value', () => {
        const formGroup = service.createSaathratriEntity2FormGroup();

        const saathratriEntity2 = service.getSaathratriEntity2(formGroup);

        expect(saathratriEntity2).toMatchObject({});
      });

      it('should return ISaathratriEntity2', () => {
        const formGroup = service.createSaathratriEntity2FormGroup(sampleWithRequiredData);

        const saathratriEntity2 = service.getSaathratriEntity2(formGroup);

        expect(saathratriEntity2).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISaathratriEntity2 should keep the key control disabled', () => {
        const formGroup = service.createSaathratriEntity2FormGroup(sampleWithRequiredData);
        expect(formGroup.controls.compositeId.controls.entityTypeId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.compositeId.controls.entityTypeId.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createSaathratriEntity2FormGroup();

        service.resetForm(formGroup, { compositeId: { entityTypeId: null, yearOfDateAdded: null, arrivalDate: null, blogId: null } });

        expect(formGroup.controls.compositeId.controls.entityTypeId.disabled).toBe(true);
      });
    });
  });
});
