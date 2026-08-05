import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IAddOnsSelectedByOrganization } from '../add-ons-selected-by-organization.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../add-ons-selected-by-organization.test-samples';

import { AddOnsSelectedByOrganizationService } from './add-ons-selected-by-organization.service';

const requireRestSample: IAddOnsSelectedByOrganization = {
  ...sampleWithRequiredData,
};

describe('AddOnsSelectedByOrganization Service', () => {
  let service: AddOnsSelectedByOrganizationService;
  let httpMock: HttpTestingController;
  let expectedResult: IAddOnsSelectedByOrganization | IAddOnsSelectedByOrganization[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AddOnsSelectedByOrganizationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('organizationId', 1, 'accountNumber', 'createdTimeId').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AddOnsSelectedByOrganization', () => {
      const addOnsSelectedByOrganization = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(addOnsSelectedByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AddOnsSelectedByOrganization', () => {
      const addOnsSelectedByOrganization = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(addOnsSelectedByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AddOnsSelectedByOrganization', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AddOnsSelectedByOrganization', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AddOnsSelectedByOrganization', () => {
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
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDatePageable('organizationId', 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberPageable(
            'organizationId',
            1,
            'accountNumber',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdPageable(
            'organizationId',
            1,
            'accountNumber',
            'createdTimeId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanPageable('organizationId', 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateLessThanEqualPageable('organizationId', 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanPageable('organizationId', 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateGreaterThanEqualPageable('organizationId', 1)
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanPageable(
            'organizationId',
            1,
            'accountNumber',
            'createdTimeId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdLessThanEqualPageable(
            'organizationId',
            1,
            'accountNumber',
            'createdTimeId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanPageable(
            'organizationId',
            1,
            'accountNumber',
            'createdTimeId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeIdGreaterThanEqualPageable(
            'organizationId',
            1,
            'accountNumber',
            'createdTimeId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findByCompositeIdOrganizationIdAndCompositeIdArrivalDateAndCompositeIdAccountNumberAndCompositeIdCreatedTimeId(
            'organizationId',
            1,
            'accountNumber',
            'createdTimeId',
          )
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ ...sampleWithRequiredData });
      });
    });

    describe('addAddOnsSelectedByOrganizationToCollectionIfMissing', () => {
      it('should add a AddOnsSelectedByOrganization to an empty array', () => {
        const addOnsSelectedByOrganization: IAddOnsSelectedByOrganization = sampleWithRequiredData;
        expectedResult = service.addAddOnsSelectedByOrganizationToCollectionIfMissing([], addOnsSelectedByOrganization);
        expect(expectedResult).toEqual([addOnsSelectedByOrganization]);
      });

      it('should not add a AddOnsSelectedByOrganization to an array that contains it', () => {
        const addOnsSelectedByOrganization: IAddOnsSelectedByOrganization = sampleWithRequiredData;
        const addOnsSelectedByOrganizationCollection: IAddOnsSelectedByOrganization[] = [
          {
            ...addOnsSelectedByOrganization,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAddOnsSelectedByOrganizationToCollectionIfMissing(
          addOnsSelectedByOrganizationCollection,
          addOnsSelectedByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AddOnsSelectedByOrganization to an array that doesn't contain it", () => {
        const addOnsSelectedByOrganization: IAddOnsSelectedByOrganization = sampleWithRequiredData;
        const addOnsSelectedByOrganizationCollection: IAddOnsSelectedByOrganization[] = [sampleWithPartialData];
        expectedResult = service.addAddOnsSelectedByOrganizationToCollectionIfMissing(
          addOnsSelectedByOrganizationCollection,
          addOnsSelectedByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(addOnsSelectedByOrganization);
      });

      it('should add only unique AddOnsSelectedByOrganization to an array', () => {
        const addOnsSelectedByOrganizationArray: IAddOnsSelectedByOrganization[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const addOnsSelectedByOrganizationCollection: IAddOnsSelectedByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addAddOnsSelectedByOrganizationToCollectionIfMissing(
          addOnsSelectedByOrganizationCollection,
          ...addOnsSelectedByOrganizationArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const addOnsSelectedByOrganization: IAddOnsSelectedByOrganization = sampleWithRequiredData;
        const addOnsSelectedByOrganization2: IAddOnsSelectedByOrganization = sampleWithPartialData;
        expectedResult = service.addAddOnsSelectedByOrganizationToCollectionIfMissing(
          [],
          addOnsSelectedByOrganization,
          addOnsSelectedByOrganization2,
        );
        expect(expectedResult).toEqual([addOnsSelectedByOrganization, addOnsSelectedByOrganization2]);
      });

      it('should accept null and undefined values', () => {
        const addOnsSelectedByOrganization: IAddOnsSelectedByOrganization = sampleWithRequiredData;
        expectedResult = service.addAddOnsSelectedByOrganizationToCollectionIfMissing([], null, addOnsSelectedByOrganization, undefined);
        expect(expectedResult).toEqual([addOnsSelectedByOrganization]);
      });

      it('should return initial array if no AddOnsSelectedByOrganization is added', () => {
        const addOnsSelectedByOrganizationCollection: IAddOnsSelectedByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addAddOnsSelectedByOrganizationToCollectionIfMissing(
          addOnsSelectedByOrganizationCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(addOnsSelectedByOrganizationCollection);
      });
    });

    describe('compareAddOnsSelectedByOrganization', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAddOnsSelectedByOrganization(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = null;

        const compareResult1 = service.compareAddOnsSelectedByOrganization(entity1, entity2);
        const compareResult2 = service.compareAddOnsSelectedByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithPartialData.compositeId };

        const compareResult1 = service.compareAddOnsSelectedByOrganization(entity1, entity2);
        const compareResult2 = service.compareAddOnsSelectedByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithRequiredData.compositeId };

        const compareResult1 = service.compareAddOnsSelectedByOrganization(entity1, entity2);
        const compareResult2 = service.compareAddOnsSelectedByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
