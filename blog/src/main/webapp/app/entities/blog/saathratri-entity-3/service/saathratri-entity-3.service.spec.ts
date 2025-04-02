import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

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
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SaathratriEntity3Service);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

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
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSaathratriEntity3ToCollectionIfMissing', () => {
      it('should add a SaathratriEntity3 to an empty array', () => {
        const saathratriEntity3: ISaathratriEntity3 = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntity3ToCollectionIfMissing([], saathratriEntity3);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saathratriEntity3);
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
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saathratriEntity3);
        expect(expectedResult).toContain(saathratriEntity32);
      });

      it('should accept null and undefined values', () => {
        const saathratriEntity3: ISaathratriEntity3 = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntity3ToCollectionIfMissing([], null, saathratriEntity3, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saathratriEntity3);
      });

      it('should return initial array if no SaathratriEntity3 is added', () => {
        const saathratriEntity3Collection: ISaathratriEntity3[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntity3ToCollectionIfMissing(saathratriEntity3Collection, undefined, null);
        expect(expectedResult).toEqual(saathratriEntity3Collection);
      });
    });

    describe('compareSaathratriEntity3', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSaathratriEntity3(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { entityType: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareSaathratriEntity3(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity3(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { entityType: 'ABC' };
        const entity2 = { entityType: 'CBA' };

        const compareResult1 = service.compareSaathratriEntity3(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity3(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { entityType: 'ABC' };
        const entity2 = { entityType: 'ABC' };

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
