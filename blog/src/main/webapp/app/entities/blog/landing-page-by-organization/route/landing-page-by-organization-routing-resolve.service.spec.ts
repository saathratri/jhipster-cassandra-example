import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ILandingPageByOrganization } from '../landing-page-by-organization.model';
import { LandingPageByOrganizationService } from '../service/landing-page-by-organization.service';

import landingPageByOrganizationResolve from './landing-page-by-organization-routing-resolve.service';

describe('LandingPageByOrganization routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: LandingPageByOrganizationService;
  let resultLandingPageByOrganization: ILandingPageByOrganization | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(LandingPageByOrganizationService);
    resultLandingPageByOrganization = undefined;
  });

  describe('resolve', () => {
    it('should return ILandingPageByOrganization returned by find', () => {
      // GIVEN
      service.find = jest.fn(organizationId => of(new HttpResponse({ body: { organizationId } })));
      mockActivatedRouteSnapshot.params = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        landingPageByOrganizationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultLandingPageByOrganization = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultLandingPageByOrganization).toEqual({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        landingPageByOrganizationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultLandingPageByOrganization = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultLandingPageByOrganization).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ILandingPageByOrganization>({ body: null })));
      mockActivatedRouteSnapshot.params = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        landingPageByOrganizationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultLandingPageByOrganization = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultLandingPageByOrganization).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
