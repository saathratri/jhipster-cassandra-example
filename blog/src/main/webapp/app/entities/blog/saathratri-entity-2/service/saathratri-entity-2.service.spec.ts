import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISaathratriEntity2 } from '../saathratri-entity-2.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../saathratri-entity-2.test-samples';

import { SaathratriEntity2Service } from './saathratri-entity-2.service';

const requireRestSample: ISaathratriEntity2 = {
  ...sampleWithRequiredData,
};

describe('SaathratriEntity2 Service', () => {
  let service: SaathratriEntity2Service;
  let httpMock: HttpTestingController;
  let expectedResult: ISaathratriEntity2 | ISaathratriEntity2[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SaathratriEntity2Service);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a SaathratriEntity2', () => {
      const saathratriEntity2 = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(saathratriEntity2).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SaathratriEntity2', () => {
      const saathratriEntity2 = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(saathratriEntity2).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SaathratriEntity2', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SaathratriEntity2', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SaathratriEntity2', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSaathratriEntity2ToCollectionIfMissing', () => {
      it('should add a SaathratriEntity2 to an empty array', () => {
        const saathratriEntity2: ISaathratriEntity2 = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntity2ToCollectionIfMissing([], saathratriEntity2);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saathratriEntity2);
      });

      it('should not add a SaathratriEntity2 to an array that contains it', () => {
        const saathratriEntity2: ISaathratriEntity2 = sampleWithRequiredData;
        const saathratriEntity2Collection: ISaathratriEntity2[] = [
          {
            ...saathratriEntity2,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSaathratriEntity2ToCollectionIfMissing(saathratriEntity2Collection, saathratriEntity2);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SaathratriEntity2 to an array that doesn't contain it", () => {
        const saathratriEntity2: ISaathratriEntity2 = sampleWithRequiredData;
        const saathratriEntity2Collection: ISaathratriEntity2[] = [sampleWithPartialData];
        expectedResult = service.addSaathratriEntity2ToCollectionIfMissing(saathratriEntity2Collection, saathratriEntity2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saathratriEntity2);
      });

      it('should add only unique SaathratriEntity2 to an array', () => {
        const saathratriEntity2Array: ISaathratriEntity2[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const saathratriEntity2Collection: ISaathratriEntity2[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntity2ToCollectionIfMissing(saathratriEntity2Collection, ...saathratriEntity2Array);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const saathratriEntity2: ISaathratriEntity2 = sampleWithRequiredData;
        const saathratriEntity22: ISaathratriEntity2 = sampleWithPartialData;
        expectedResult = service.addSaathratriEntity2ToCollectionIfMissing([], saathratriEntity2, saathratriEntity22);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saathratriEntity2);
        expect(expectedResult).toContain(saathratriEntity22);
      });

      it('should accept null and undefined values', () => {
        const saathratriEntity2: ISaathratriEntity2 = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntity2ToCollectionIfMissing([], null, saathratriEntity2, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saathratriEntity2);
      });

      it('should return initial array if no SaathratriEntity2 is added', () => {
        const saathratriEntity2Collection: ISaathratriEntity2[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntity2ToCollectionIfMissing(saathratriEntity2Collection, undefined, null);
        expect(expectedResult).toEqual(saathratriEntity2Collection);
      });
    });

    describe('compareSaathratriEntity2', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSaathratriEntity2(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { entityTypeId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareSaathratriEntity2(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity2(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { entityTypeId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { entityTypeId: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareSaathratriEntity2(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity2(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { entityTypeId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { entityTypeId: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareSaathratriEntity2(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity2(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
