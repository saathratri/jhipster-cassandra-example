import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ISaathratriEntity3 } from '../saathratri-entity-3.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../saathratri-entity-3.test-samples';

import { SaathratriEntity3Service } from './saathratri-entity-3.service';

const requireRestSample: ISaathratriEntity3 = {
  ...sampleWithRequiredData,
};

describe('SaathratriEntity3 Service', () => {
  let service: SaathratriEntity3Service;
  let httpMock: HttpTestingController;
  let expectedResult: ISaathratriEntity3 | ISaathratriEntity3[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SaathratriEntity3Service);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('entityType', 'createdTimeId').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a SaathratriEntity3', () => {
      const saathratriEntity3 = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(saathratriEntity3).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SaathratriEntity3', () => {
      const saathratriEntity3 = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(saathratriEntity3).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SaathratriEntity3', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SaathratriEntity3', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SaathratriEntity3', () => {
      service.delete(sampleWithRequiredData).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('composite-key search methods', () => {
      it('should call findAllByCompositeIdEntityTypePageable', () => {
        const returnedFromService = { ...requireRestSample };

        service.findAllByCompositeIdEntityTypePageable('entityType').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdPageable('entityType', 'createdTimeId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanPageable('entityType', 'createdTimeId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdLessThanEqualPageable('entityType', 'createdTimeId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanPageable('entityType', 'createdTimeId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdEntityTypeAndCompositeIdCreatedTimeIdGreaterThanEqualPageable('entityType', 'createdTimeId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findByCompositeIdEntityTypeAndCompositeIdCreatedTimeId('entityType', 'createdTimeId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ ...sampleWithRequiredData });
      });
    });

    describe('addSaathratriEntity3ToCollectionIfMissing', () => {
      it('should add a SaathratriEntity3 to an empty array', () => {
        const saathratriEntity3: ISaathratriEntity3 = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntity3ToCollectionIfMissing([], saathratriEntity3);
        expect(expectedResult).toEqual([saathratriEntity3]);
      });

      it('should not add a SaathratriEntity3 to an array that contains it', () => {
        const saathratriEntity3: ISaathratriEntity3 = sampleWithRequiredData;
        const saathratriEntity3Collection: ISaathratriEntity3[] = [
          {
            ...saathratriEntity3,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSaathratriEntity3ToCollectionIfMissing(saathratriEntity3Collection, saathratriEntity3);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SaathratriEntity3 to an array that doesn't contain it", () => {
        const saathratriEntity3: ISaathratriEntity3 = sampleWithRequiredData;
        const saathratriEntity3Collection: ISaathratriEntity3[] = [sampleWithPartialData];
        expectedResult = service.addSaathratriEntity3ToCollectionIfMissing(saathratriEntity3Collection, saathratriEntity3);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saathratriEntity3);
      });

      it('should add only unique SaathratriEntity3 to an array', () => {
        const saathratriEntity3Array: ISaathratriEntity3[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const saathratriEntity3Collection: ISaathratriEntity3[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntity3ToCollectionIfMissing(saathratriEntity3Collection, ...saathratriEntity3Array);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const saathratriEntity3: ISaathratriEntity3 = sampleWithRequiredData;
        const saathratriEntity32: ISaathratriEntity3 = sampleWithPartialData;
        expectedResult = service.addSaathratriEntity3ToCollectionIfMissing([], saathratriEntity3, saathratriEntity32);
        expect(expectedResult).toEqual([saathratriEntity3, saathratriEntity32]);
      });

      it('should accept null and undefined values', () => {
        const saathratriEntity3: ISaathratriEntity3 = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntity3ToCollectionIfMissing([], null, saathratriEntity3, undefined);
        expect(expectedResult).toEqual([saathratriEntity3]);
      });

      it('should return initial array if no SaathratriEntity3 is added', () => {
        const saathratriEntity3Collection: ISaathratriEntity3[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntity3ToCollectionIfMissing(saathratriEntity3Collection, undefined, null);
        expect(expectedResult).toEqual(saathratriEntity3Collection);
      });
    });

    describe('compareSaathratriEntity3', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSaathratriEntity3(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = null;

        const compareResult1 = service.compareSaathratriEntity3(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity3(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithPartialData.compositeId };

        const compareResult1 = service.compareSaathratriEntity3(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity3(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithRequiredData.compositeId };

        const compareResult1 = service.compareSaathratriEntity3(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity3(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
