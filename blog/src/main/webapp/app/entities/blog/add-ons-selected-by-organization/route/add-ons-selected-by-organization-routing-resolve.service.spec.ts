import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAddOnsSelectedByOrganization } from '../add-ons-selected-by-organization.model';
import { AddOnsSelectedByOrganizationService } from '../service/add-ons-selected-by-organization.service';

import addOnsSelectedByOrganizationResolve from './add-ons-selected-by-organization-routing-resolve.service';

describe('AddOnsSelectedByOrganization routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AddOnsSelectedByOrganizationService;
  let resultAddOnsSelectedByOrganization: IAddOnsSelectedByOrganization | null | undefined;

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
    service = TestBed.inject(AddOnsSelectedByOrganizationService);
    resultAddOnsSelectedByOrganization = undefined;
  });

  describe('resolve', () => {
    it('should return IAddOnsSelectedByOrganization returned by find', () => {
      // GIVEN
      service.find = jest.fn(organizationId => of(new HttpResponse({ body: { organizationId } })));
      mockActivatedRouteSnapshot.params = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        addOnsSelectedByOrganizationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAddOnsSelectedByOrganization = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultAddOnsSelectedByOrganization).toEqual({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        addOnsSelectedByOrganizationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAddOnsSelectedByOrganization = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultAddOnsSelectedByOrganization).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAddOnsSelectedByOrganization>({ body: null })));
      mockActivatedRouteSnapshot.params = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        addOnsSelectedByOrganizationResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAddOnsSelectedByOrganization = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultAddOnsSelectedByOrganization).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
