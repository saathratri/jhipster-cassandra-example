import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ILandingPageByOrganization } from '../landing-page-by-organization.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../landing-page-by-organization.test-samples';

import { LandingPageByOrganizationService } from './landing-page-by-organization.service';

const requireRestSample: ILandingPageByOrganization = {
  ...sampleWithRequiredData,
};

describe('LandingPageByOrganization Service', () => {
  let service: LandingPageByOrganizationService;
  let httpMock: HttpTestingController;
  let expectedResult: ILandingPageByOrganization | ILandingPageByOrganization[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(LandingPageByOrganizationService);
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

    it('should create a LandingPageByOrganization', () => {
      const landingPageByOrganization = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(landingPageByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LandingPageByOrganization', () => {
      const landingPageByOrganization = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(landingPageByOrganization).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LandingPageByOrganization', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LandingPageByOrganization', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LandingPageByOrganization', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLandingPageByOrganizationToCollectionIfMissing', () => {
      it('should add a LandingPageByOrganization to an empty array', () => {
        const landingPageByOrganization: ILandingPageByOrganization = sampleWithRequiredData;
        expectedResult = service.addLandingPageByOrganizationToCollectionIfMissing([], landingPageByOrganization);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(landingPageByOrganization);
      });

      it('should not add a LandingPageByOrganization to an array that contains it', () => {
        const landingPageByOrganization: ILandingPageByOrganization = sampleWithRequiredData;
        const landingPageByOrganizationCollection: ILandingPageByOrganization[] = [
          {
            ...landingPageByOrganization,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLandingPageByOrganizationToCollectionIfMissing(
          landingPageByOrganizationCollection,
          landingPageByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LandingPageByOrganization to an array that doesn't contain it", () => {
        const landingPageByOrganization: ILandingPageByOrganization = sampleWithRequiredData;
        const landingPageByOrganizationCollection: ILandingPageByOrganization[] = [sampleWithPartialData];
        expectedResult = service.addLandingPageByOrganizationToCollectionIfMissing(
          landingPageByOrganizationCollection,
          landingPageByOrganization,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(landingPageByOrganization);
      });

      it('should add only unique LandingPageByOrganization to an array', () => {
        const landingPageByOrganizationArray: ILandingPageByOrganization[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const landingPageByOrganizationCollection: ILandingPageByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addLandingPageByOrganizationToCollectionIfMissing(
          landingPageByOrganizationCollection,
          ...landingPageByOrganizationArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const landingPageByOrganization: ILandingPageByOrganization = sampleWithRequiredData;
        const landingPageByOrganization2: ILandingPageByOrganization = sampleWithPartialData;
        expectedResult = service.addLandingPageByOrganizationToCollectionIfMissing(
          [],
          landingPageByOrganization,
          landingPageByOrganization2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(landingPageByOrganization);
        expect(expectedResult).toContain(landingPageByOrganization2);
      });

      it('should accept null and undefined values', () => {
        const landingPageByOrganization: ILandingPageByOrganization = sampleWithRequiredData;
        expectedResult = service.addLandingPageByOrganizationToCollectionIfMissing([], null, landingPageByOrganization, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(landingPageByOrganization);
      });

      it('should return initial array if no LandingPageByOrganization is added', () => {
        const landingPageByOrganizationCollection: ILandingPageByOrganization[] = [sampleWithRequiredData];
        expectedResult = service.addLandingPageByOrganizationToCollectionIfMissing(landingPageByOrganizationCollection, undefined, null);
        expect(expectedResult).toEqual(landingPageByOrganizationCollection);
      });
    });

    describe('compareLandingPageByOrganization', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLandingPageByOrganization(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareLandingPageByOrganization(entity1, entity2);
        const compareResult2 = service.compareLandingPageByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { organizationId: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareLandingPageByOrganization(entity1, entity2);
        const compareResult2 = service.compareLandingPageByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareLandingPageByOrganization(entity1, entity2);
        const compareResult2 = service.compareLandingPageByOrganization(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
