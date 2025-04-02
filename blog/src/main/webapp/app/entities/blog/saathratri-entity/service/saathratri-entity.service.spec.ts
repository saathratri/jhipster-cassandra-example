import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISaathratriEntity } from '../saathratri-entity.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../saathratri-entity.test-samples';

import { SaathratriEntityService } from './saathratri-entity.service';

const requireRestSample: ISaathratriEntity = {
  ...sampleWithRequiredData,
};

describe('SaathratriEntity Service', () => {
  let service: SaathratriEntityService;
  let httpMock: HttpTestingController;
  let expectedResult: ISaathratriEntity | ISaathratriEntity[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SaathratriEntityService);
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

    it('should create a SaathratriEntity', () => {
      const saathratriEntity = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(saathratriEntity).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SaathratriEntity', () => {
      const saathratriEntity = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(saathratriEntity).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SaathratriEntity', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SaathratriEntity', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SaathratriEntity', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSaathratriEntityToCollectionIfMissing', () => {
      it('should add a SaathratriEntity to an empty array', () => {
        const saathratriEntity: ISaathratriEntity = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntityToCollectionIfMissing([], saathratriEntity);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saathratriEntity);
      });

      it('should not add a SaathratriEntity to an array that contains it', () => {
        const saathratriEntity: ISaathratriEntity = sampleWithRequiredData;
        const saathratriEntityCollection: ISaathratriEntity[] = [
          {
            ...saathratriEntity,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSaathratriEntityToCollectionIfMissing(saathratriEntityCollection, saathratriEntity);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SaathratriEntity to an array that doesn't contain it", () => {
        const saathratriEntity: ISaathratriEntity = sampleWithRequiredData;
        const saathratriEntityCollection: ISaathratriEntity[] = [sampleWithPartialData];
        expectedResult = service.addSaathratriEntityToCollectionIfMissing(saathratriEntityCollection, saathratriEntity);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saathratriEntity);
      });

      it('should add only unique SaathratriEntity to an array', () => {
        const saathratriEntityArray: ISaathratriEntity[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const saathratriEntityCollection: ISaathratriEntity[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntityToCollectionIfMissing(saathratriEntityCollection, ...saathratriEntityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const saathratriEntity: ISaathratriEntity = sampleWithRequiredData;
        const saathratriEntity2: ISaathratriEntity = sampleWithPartialData;
        expectedResult = service.addSaathratriEntityToCollectionIfMissing([], saathratriEntity, saathratriEntity2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(saathratriEntity);
        expect(expectedResult).toContain(saathratriEntity2);
      });

      it('should accept null and undefined values', () => {
        const saathratriEntity: ISaathratriEntity = sampleWithRequiredData;
        expectedResult = service.addSaathratriEntityToCollectionIfMissing([], null, saathratriEntity, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(saathratriEntity);
      });

      it('should return initial array if no SaathratriEntity is added', () => {
        const saathratriEntityCollection: ISaathratriEntity[] = [sampleWithRequiredData];
        expectedResult = service.addSaathratriEntityToCollectionIfMissing(saathratriEntityCollection, undefined, null);
        expect(expectedResult).toEqual(saathratriEntityCollection);
      });
    });

    describe('compareSaathratriEntity', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSaathratriEntity(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { entityId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareSaathratriEntity(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { entityId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { entityId: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareSaathratriEntity(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { entityId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { entityId: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareSaathratriEntity(entity1, entity2);
        const compareResult2 = service.compareSaathratriEntity(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
