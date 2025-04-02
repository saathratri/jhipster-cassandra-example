import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISaathratriEntity4 } from '../saathratri-entity-4.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../saathratri-entity-4.test-samples';

import { SaathratriEntity4Service } from './saathratri-entity-4.service';

const requireRestSample: ISaathratriEntity4 = {
  ...sampleWithRequiredData,
};

describe('SaathratriEntity4 Service', () => {
  let service: SaathratriEntity4Service;
  let httpMock: HttpTestingController;
  let expectedResult: ISaathratriEntity4 | ISaathratriEntity4[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SaathratriEntity4Service);
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

    it('should create a SaathratriEntity4', () => {
      const saathratriEntity4 = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(saathratriEntity4).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SaathratriEntity4', () => {
      const saathratriEntity4 = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(saathratriEntity4).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SaathratriEntity4', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SaathratriEntity4', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SaathratriEntity4', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSaathratriEntity4ToCollectionIfMissing', () => {
      it('should add a SaathratriEntity4 to an empty array', () => {
        const saathratriEntity4: ISaathratriEntity4 = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntity4ToCollectionIfMissing([], saathratriEntity4);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saathratriEntity4);
      });

      it('should not add a SaathratriEntity4 to an array that contains it', () => {
        const saathratriEntity4: ISaathratriEntity4 = sampleWithRequiredData;
        const saathratriEntity4Collection: ISaathratriEntity4[] = [
          {
            ...saathratriEntity4,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSaathratriEntity4ToCollectionIfMissing(saathratriEntity4Collection, saathratriEntity4);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SaathratriEntity4 to an array that doesn't contain it", () => {
        const saathratriEntity4: ISaathratriEntity4 = sampleWithRequiredData;
        const saathratriEntity4Collection: ISaathratriEntity4[] = [sampleWithPartialData];
        expectedResult = service.addSaathratriEntity4ToCollectionIfMissing(saathratriEntity4Collection, saathratriEntity4);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saathratriEntity4);
      });

      it('should add only unique SaathratriEntity4 to an array', () => {
        const saathratriEntity4Array: ISaathratriEntity4[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const saathratriEntity4Collection: ISaathratriEntity4[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntity4ToCollectionIfMissing(saathratriEntity4Collection, ...saathratriEntity4Array);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const saathratriEntity4: ISaathratriEntity4 = sampleWithRequiredData;
        const saathratriEntity42: ISaathratriEntity4 = sampleWithPartialData;
        expectedResult = service.addSaathratriEntity4ToCollectionIfMissing([], saathratriEntity4, saathratriEntity42);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saathratriEntity4);
        expect(expectedResult).toContain(saathratriEntity42);
      });

      it('should accept null and undefined values', () => {
        const saathratriEntity4: ISaathratriEntity4 = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntity4ToCollectionIfMissing([], null, saathratriEntity4, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saathratriEntity4);
      });

      it('should return initial array if no SaathratriEntity4 is added', () => {
        const saathratriEntity4Collection: ISaathratriEntity4[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntity4ToCollectionIfMissing(saathratriEntity4Collection, undefined, null);
        expect(expectedResult).toEqual(saathratriEntity4Collection);
      });
    });

    describe('compareSaathratriEntity4', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSaathratriEntity4(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareSaathratriEntity4(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity4(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { organizationId: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareSaathratriEntity4(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity4(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareSaathratriEntity4(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity4(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
