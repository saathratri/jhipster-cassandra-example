import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISaathratriEntity5 } from '../saathratri-entity-5.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../saathratri-entity-5.test-samples';

import { SaathratriEntity5Service } from './saathratri-entity-5.service';

const requireRestSample: ISaathratriEntity5 = {
  ...sampleWithRequiredData,
};

describe('SaathratriEntity5 Service', () => {
  let service: SaathratriEntity5Service;
  let httpMock: HttpTestingController;
  let expectedResult: ISaathratriEntity5 | ISaathratriEntity5[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SaathratriEntity5Service);
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

    it('should create a SaathratriEntity5', () => {
      const saathratriEntity5 = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(saathratriEntity5).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SaathratriEntity5', () => {
      const saathratriEntity5 = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(saathratriEntity5).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SaathratriEntity5', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SaathratriEntity5', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SaathratriEntity5', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSaathratriEntity5ToCollectionIfMissing', () => {
      it('should add a SaathratriEntity5 to an empty array', () => {
        const saathratriEntity5: ISaathratriEntity5 = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntity5ToCollectionIfMissing([], saathratriEntity5);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saathratriEntity5);
      });

      it('should not add a SaathratriEntity5 to an array that contains it', () => {
        const saathratriEntity5: ISaathratriEntity5 = sampleWithRequiredData;
        const saathratriEntity5Collection: ISaathratriEntity5[] = [
          {
            ...saathratriEntity5,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSaathratriEntity5ToCollectionIfMissing(saathratriEntity5Collection, saathratriEntity5);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SaathratriEntity5 to an array that doesn't contain it", () => {
        const saathratriEntity5: ISaathratriEntity5 = sampleWithRequiredData;
        const saathratriEntity5Collection: ISaathratriEntity5[] = [sampleWithPartialData];
        expectedResult = service.addSaathratriEntity5ToCollectionIfMissing(saathratriEntity5Collection, saathratriEntity5);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saathratriEntity5);
      });

      it('should add only unique SaathratriEntity5 to an array', () => {
        const saathratriEntity5Array: ISaathratriEntity5[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const saathratriEntity5Collection: ISaathratriEntity5[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntity5ToCollectionIfMissing(saathratriEntity5Collection, ...saathratriEntity5Array);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const saathratriEntity5: ISaathratriEntity5 = sampleWithRequiredData;
        const saathratriEntity52: ISaathratriEntity5 = sampleWithPartialData;
        expectedResult = service.addSaathratriEntity5ToCollectionIfMissing([], saathratriEntity5, saathratriEntity52);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saathratriEntity5);
        expect(expectedResult).toContain(saathratriEntity52);
      });

      it('should accept null and undefined values', () => {
        const saathratriEntity5: ISaathratriEntity5 = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntity5ToCollectionIfMissing([], null, saathratriEntity5, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saathratriEntity5);
      });

      it('should return initial array if no SaathratriEntity5 is added', () => {
        const saathratriEntity5Collection: ISaathratriEntity5[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntity5ToCollectionIfMissing(saathratriEntity5Collection, undefined, null);
        expect(expectedResult).toEqual(saathratriEntity5Collection);
      });
    });

    describe('compareSaathratriEntity5', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSaathratriEntity5(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareSaathratriEntity5(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity5(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { organizationId: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareSaathratriEntity5(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity5(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareSaathratriEntity5(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity5(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
