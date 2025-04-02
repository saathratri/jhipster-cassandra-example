import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISaathratriEntity6 } from '../saathratri-entity-6.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../saathratri-entity-6.test-samples';

import { SaathratriEntity6Service } from './saathratri-entity-6.service';

const requireRestSample: ISaathratriEntity6 = {
  ...sampleWithRequiredData,
};

describe('SaathratriEntity6 Service', () => {
  let service: SaathratriEntity6Service;
  let httpMock: HttpTestingController;
  let expectedResult: ISaathratriEntity6 | ISaathratriEntity6[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SaathratriEntity6Service);
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

    it('should create a SaathratriEntity6', () => {
      const saathratriEntity6 = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(saathratriEntity6).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SaathratriEntity6', () => {
      const saathratriEntity6 = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(saathratriEntity6).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SaathratriEntity6', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SaathratriEntity6', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SaathratriEntity6', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSaathratriEntity6ToCollectionIfMissing', () => {
      it('should add a SaathratriEntity6 to an empty array', () => {
        const saathratriEntity6: ISaathratriEntity6 = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntity6ToCollectionIfMissing([], saathratriEntity6);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saathratriEntity6);
      });

      it('should not add a SaathratriEntity6 to an array that contains it', () => {
        const saathratriEntity6: ISaathratriEntity6 = sampleWithRequiredData;
        const saathratriEntity6Collection: ISaathratriEntity6[] = [
          {
            ...saathratriEntity6,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSaathratriEntity6ToCollectionIfMissing(saathratriEntity6Collection, saathratriEntity6);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SaathratriEntity6 to an array that doesn't contain it", () => {
        const saathratriEntity6: ISaathratriEntity6 = sampleWithRequiredData;
        const saathratriEntity6Collection: ISaathratriEntity6[] = [sampleWithPartialData];
        expectedResult = service.addSaathratriEntity6ToCollectionIfMissing(saathratriEntity6Collection, saathratriEntity6);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saathratriEntity6);
      });

      it('should add only unique SaathratriEntity6 to an array', () => {
        const saathratriEntity6Array: ISaathratriEntity6[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const saathratriEntity6Collection: ISaathratriEntity6[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntity6ToCollectionIfMissing(saathratriEntity6Collection, ...saathratriEntity6Array);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const saathratriEntity6: ISaathratriEntity6 = sampleWithRequiredData;
        const saathratriEntity62: ISaathratriEntity6 = sampleWithPartialData;
        expectedResult = service.addSaathratriEntity6ToCollectionIfMissing([], saathratriEntity6, saathratriEntity62);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saathratriEntity6);
        expect(expectedResult).toContain(saathratriEntity62);
      });

      it('should accept null and undefined values', () => {
        const saathratriEntity6: ISaathratriEntity6 = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntity6ToCollectionIfMissing([], null, saathratriEntity6, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saathratriEntity6);
      });

      it('should return initial array if no SaathratriEntity6 is added', () => {
        const saathratriEntity6Collection: ISaathratriEntity6[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntity6ToCollectionIfMissing(saathratriEntity6Collection, undefined, null);
        expect(expectedResult).toEqual(saathratriEntity6Collection);
      });
    });

    describe('compareSaathratriEntity6', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSaathratriEntity6(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareSaathratriEntity6(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity6(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { organizationId: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareSaathratriEntity6(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity6(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareSaathratriEntity6(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity6(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
