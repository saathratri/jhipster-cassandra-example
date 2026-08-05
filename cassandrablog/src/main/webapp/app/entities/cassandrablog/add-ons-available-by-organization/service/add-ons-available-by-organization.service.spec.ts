import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IAddOnsAvailableByOrganization } from '../add-ons-available-by-organization.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../add-ons-available-by-organization.test-samples';

import { AddOnsAvailableByOrganizationService } from './add-ons-available-by-organization.service';

const requireRestSample: IAddOnsAvailableByOrganization = {
  ...sampleWithRequiredData,
};

describe('AddOnsAvailableByOrganization Service', () => {
  let service: AddOnsAvailableByOrganizationService;
  let httpMock: HttpTestingController;
  let expectedResult: IAddOnsAvailableByOrganization | IAddOnsAvailableByOrganization[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AddOnsAvailableByOrganizationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('organizationId', 'entityType', 'entityId', 'addOnId').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AddOnsAvailableByOrganization', () => {
      const addOnsAvailableByOrganization = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(addOnsAvailableByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AddOnsAvailableByOrganization', () => {
      const addOnsAvailableByOrganization = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(addOnsAvailableByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AddOnsAvailableByOrganization', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AddOnsAvailableByOrganization', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AddOnsAvailableByOrganization', () => {
      service.delete(sampleWithRequiredData).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('composite-key search methods', () => {
      it('should call findAllByCompositeIdOrganizationIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service.findAllByCompositeIdOrganizationIdPageable('organizationId').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypePageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypePageable('organizationId', 'entityType')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdPageable(
            'organizationId',
            'entityType',
            'entityId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnIdPageable(
            'organizationId',
            'entityType',
            'entityId',
            'addOnId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findByCompositeIdOrganizationIdAndCompositeIdEntityTypeAndCompositeIdEntityIdAndCompositeIdAddOnId(
            'organizationId',
            'entityType',
            'entityId',
            'addOnId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ ...sampleWithRequiredData });
      });
    });

    describe('addAddOnsAvailableByOrganizationToCollectionIfMissing', () => {
      it('should add a AddOnsAvailableByOrganization to an empty array', () => {
        const addOnsAvailableByOrganization: IAddOnsAvailableByOrganization = sampleWithRequiredData;
        expectedResult = service.addAddOnsAvailableByOrganizationToCollectionIfMissing([], addOnsAvailableByOrganization);
        expect(expectedResult).toEqual([addOnsAvailableByOrganization]);
      });

      it('should not add a AddOnsAvailableByOrganization to an array that contains it', () => {
        const addOnsAvailableByOrganization: IAddOnsAvailableByOrganization = sampleWithRequiredData;
        const addOnsAvailableByOrganizationCollection: IAddOnsAvailableByOrganization[] = [
          {
            ...addOnsAvailableByOrganization,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAddOnsAvailableByOrganizationToCollectionIfMissing(
          addOnsAvailableByOrganizationCollection,
          addOnsAvailableByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AddOnsAvailableByOrganization to an array that doesn't contain it", () => {
        const addOnsAvailableByOrganization: IAddOnsAvailableByOrganization = sampleWithRequiredData;
        const addOnsAvailableByOrganizationCollection: IAddOnsAvailableByOrganization[] = [sampleWithPartialData];
        expectedResult = service.addAddOnsAvailableByOrganizationToCollectionIfMissing(
          addOnsAvailableByOrganizationCollection,
          addOnsAvailableByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(addOnsAvailableByOrganization);
      });

      it('should add only unique AddOnsAvailableByOrganization to an array', () => {
        const addOnsAvailableByOrganizationArray: IAddOnsAvailableByOrganization[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const addOnsAvailableByOrganizationCollection: IAddOnsAvailableByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addAddOnsAvailableByOrganizationToCollectionIfMissing(
          addOnsAvailableByOrganizationCollection,
          ...addOnsAvailableByOrganizationArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const addOnsAvailableByOrganization: IAddOnsAvailableByOrganization = sampleWithRequiredData;
        const addOnsAvailableByOrganization2: IAddOnsAvailableByOrganization = sampleWithPartialData;
        expectedResult = service.addAddOnsAvailableByOrganizationToCollectionIfMissing(
          [],
          addOnsAvailableByOrganization,
          addOnsAvailableByOrganization2,
        );
        expect(expectedResult).toEqual([addOnsAvailableByOrganization, addOnsAvailableByOrganization2]);
      });

      it('should accept null and undefined values', () => {
        const addOnsAvailableByOrganization: IAddOnsAvailableByOrganization = sampleWithRequiredData;
        expectedResult = service.addAddOnsAvailableByOrganizationToCollectionIfMissing([], null, addOnsAvailableByOrganization, undefined);
        expect(expectedResult).toEqual([addOnsAvailableByOrganization]);
      });

      it('should return initial array if no AddOnsAvailableByOrganization is added', () => {
        const addOnsAvailableByOrganizationCollection: IAddOnsAvailableByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addAddOnsAvailableByOrganizationToCollectionIfMissing(
          addOnsAvailableByOrganizationCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(addOnsAvailableByOrganizationCollection);
      });
    });

    describe('compareAddOnsAvailableByOrganization', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAddOnsAvailableByOrganization(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = null;

        const compareResult1 = service.compareAddOnsAvailableByOrganization(entity1, entity2);
        const compareResult2 = service.compareAddOnsAvailableByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithPartialData.compositeId };

        const compareResult1 = service.compareAddOnsAvailableByOrganization(entity1, entity2);
        const compareResult2 = service.compareAddOnsAvailableByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithRequiredData.compositeId };

        const compareResult1 = service.compareAddOnsAvailableByOrganization(entity1, entity2);
        const compareResult2 = service.compareAddOnsAvailableByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
