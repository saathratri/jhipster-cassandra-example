import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISetEntityByOrganization } from '../set-entity-by-organization.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../set-entity-by-organization.test-samples';

import { SetEntityByOrganizationService } from './set-entity-by-organization.service';

const requireRestSample: ISetEntityByOrganization = {
  ...sampleWithRequiredData,
};

describe('SetEntityByOrganization Service', () => {
  let service: SetEntityByOrganizationService;
  let httpMock: HttpTestingController;
  let expectedResult: ISetEntityByOrganization | ISetEntityByOrganization[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SetEntityByOrganizationService);
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

    it('should create a SetEntityByOrganization', () => {
      const setEntityByOrganization = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(setEntityByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SetEntityByOrganization', () => {
      const setEntityByOrganization = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(setEntityByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SetEntityByOrganization', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SetEntityByOrganization', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SetEntityByOrganization', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSetEntityByOrganizationToCollectionIfMissing', () => {
      it('should add a SetEntityByOrganization to an empty array', () => {
        const setEntityByOrganization: ISetEntityByOrganization = sampleWithRequiredData;
        expectedResult = service.addSetEntityByOrganizationToCollectionIfMissing([], setEntityByOrganization);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(setEntityByOrganization);
      });

      it('should not add a SetEntityByOrganization to an array that contains it', () => {
        const setEntityByOrganization: ISetEntityByOrganization = sampleWithRequiredData;
        const setEntityByOrganizationCollection: ISetEntityByOrganization[] = [
          {
            ...setEntityByOrganization,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSetEntityByOrganizationToCollectionIfMissing(
          setEntityByOrganizationCollection,
          setEntityByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SetEntityByOrganization to an array that doesn't contain it", () => {
        const setEntityByOrganization: ISetEntityByOrganization = sampleWithRequiredData;
        const setEntityByOrganizationCollection: ISetEntityByOrganization[] = [sampleWithPartialData];
        expectedResult = service.addSetEntityByOrganizationToCollectionIfMissing(
          setEntityByOrganizationCollection,
          setEntityByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(setEntityByOrganization);
      });

      it('should add only unique SetEntityByOrganization to an array', () => {
        const setEntityByOrganizationArray: ISetEntityByOrganization[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const setEntityByOrganizationCollection: ISetEntityByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addSetEntityByOrganizationToCollectionIfMissing(
          setEntityByOrganizationCollection,
          ...setEntityByOrganizationArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const setEntityByOrganization: ISetEntityByOrganization = sampleWithRequiredData;
        const setEntityByOrganization2: ISetEntityByOrganization = sampleWithPartialData;
        expectedResult = service.addSetEntityByOrganizationToCollectionIfMissing([], setEntityByOrganization, setEntityByOrganization2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(setEntityByOrganization);
        expect(expectedResult).toContain(setEntityByOrganization2);
      });

      it('should accept null and undefined values', () => {
        const setEntityByOrganization: ISetEntityByOrganization = sampleWithRequiredData;
        expectedResult = service.addSetEntityByOrganizationToCollectionIfMissing([], null, setEntityByOrganization, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(setEntityByOrganization);
      });

      it('should return initial array if no SetEntityByOrganization is added', () => {
        const setEntityByOrganizationCollection: ISetEntityByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addSetEntityByOrganizationToCollectionIfMissing(setEntityByOrganizationCollection, undefined, null);
        expect(expectedResult).toEqual(setEntityByOrganizationCollection);
      });
    });

    describe('compareSetEntityByOrganization', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSetEntityByOrganization(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareSetEntityByOrganization(entity1, entity2);
        const compareResult2 = service.compareSetEntityByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { organizationId: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareSetEntityByOrganization(entity1, entity2);
        const compareResult2 = service.compareSetEntityByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareSetEntityByOrganization(entity1, entity2);
        const compareResult2 = service.compareSetEntityByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
