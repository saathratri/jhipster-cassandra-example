import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../saathratri-entity-6.test-samples';

import { SaathratriEntity6FormService } from './saathratri-entity-6-form.service';

describe('SaathratriEntity6 Form Service', () => {
  let service: SaathratriEntity6FormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaathratriEntity6FormService);
  });

  describe('Service methods', () => {
    describe('createSaathratriEntity6FormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSaathratriEntity6FormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            arrivalDate: expect.any(Object),
            accountNumber: expect.any(Object),
            createdTimeId: expect.any(Object),
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

      it('passing ISaathratriEntity6 should create a new form with FormGroup', () => {
        const formGroup = service.createSaathratriEntity6FormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            organizationId: expect.any(Object),
            arrivalDate: expect.any(Object),
            accountNumber: expect.any(Object),
            createdTimeId: expect.any(Object),
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

    describe('getSaathratriEntity6', () => {
      it('should return NewSaathratriEntity6 for default SaathratriEntity6 initial value', () => {
        const formGroup = service.createSaathratriEntity6FormGroup(sampleWithNewData);

        const saathratriEntity6 = service.getSaathratriEntity6(formGroup) as any;

        expect(saathratriEntity6).toMatchObject(sampleWithNewData);
      });

      it('should return NewSaathratriEntity6 for empty SaathratriEntity6 initial value', () => {
        const formGroup = service.createSaathratriEntity6FormGroup();

        const saathratriEntity6 = service.getSaathratriEntity6(formGroup) as any;

        expect(saathratriEntity6).toMatchObject({});
      });

      it('should return ISaathratriEntity6', () => {
        const formGroup = service.createSaathratriEntity6FormGroup(sampleWithRequiredData);

        const saathratriEntity6 = service.getSaathratriEntity6(formGroup) as any;

        expect(saathratriEntity6).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISaathratriEntity6 should not enable organizationId FormControl', () => {
        const formGroup = service.createSaathratriEntity6FormGroup();
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });

      it('passing NewSaathratriEntity6 should disable organizationId FormControl', () => {
        const formGroup = service.createSaathratriEntity6FormGroup(sampleWithRequiredData);
        expect(formGroup.controls.organizationId.disabled).toBe(true);

        service.resetForm(formGroup, { organizationId: null });

        expect(formGroup.controls.organizationId.disabled).toBe(true);
      });
    });
  });
});
