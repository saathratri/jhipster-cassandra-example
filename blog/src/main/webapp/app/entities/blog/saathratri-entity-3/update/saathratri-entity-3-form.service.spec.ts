import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../saathratri-entity-3.test-samples';

import { SaathratriEntity3FormService } from './saathratri-entity-3-form.service';

describe('SaathratriEntity3 Form Service', () => {
  let service: SaathratriEntity3FormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaathratriEntity3FormService);
  });

  describe('Service methods', () => {
    describe('createSaathratriEntity3FormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSaathratriEntity3FormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            entityType: expect.any(Object),
            createdTimeId: expect.any(Object),
            entityName: expect.any(Object),
            entityDescription: expect.any(Object),
            entityCost: expect.any(Object),
            departureDate: expect.any(Object),
            tags: expect.any(Object),
          }),
        );
      });

      it('passing ISaathratriEntity3 should create a new form with FormGroup', () => {
        const formGroup = service.createSaathratriEntity3FormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            entityType: expect.any(Object),
            createdTimeId: expect.any(Object),
            entityName: expect.any(Object),
            entityDescription: expect.any(Object),
            entityCost: expect.any(Object),
            departureDate: expect.any(Object),
            tags: expect.any(Object),
          }),
        );
      });
    });

    describe('getSaathratriEntity3', () => {
      it('should return NewSaathratriEntity3 for default SaathratriEntity3 initial value', () => {
        const formGroup = service.createSaathratriEntity3FormGroup(sampleWithNewData);

        const saathratriEntity3 = service.getSaathratriEntity3(formGroup) as any;

        expect(saathratriEntity3).toMatchObject(sampleWithNewData);
      });

      it('should return NewSaathratriEntity3 for empty SaathratriEntity3 initial value', () => {
        const formGroup = service.createSaathratriEntity3FormGroup();

        const saathratriEntity3 = service.getSaathratriEntity3(formGroup) as any;

        expect(saathratriEntity3).toMatchObject({});
      });

      it('should return ISaathratriEntity3', () => {
        const formGroup = service.createSaathratriEntity3FormGroup(sampleWithRequiredData);

        const saathratriEntity3 = service.getSaathratriEntity3(formGroup) as any;

        expect(saathratriEntity3).toMatchObject(sampleWithRequiredData);
      });
    });
  });
});
