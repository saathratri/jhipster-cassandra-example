import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISaathratriEntity5 } from '../saathratri-entity-5.model';
import { SaathratriEntity5Service } from '../service/saathratri-entity-5.service';

import saathratriEntity5Resolve from './saathratri-entity-5-routing-resolve.service';

describe('SaathratriEntity5 routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SaathratriEntity5Service;
  let resultSaathratriEntity5: ISaathratriEntity5 | null | undefined;

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
    service = TestBed.inject(SaathratriEntity5Service);
    resultSaathratriEntity5 = undefined;
  });

  describe('resolve', () => {
    it('should return ISaathratriEntity5 returned by find', () => {
      // GIVEN
      service.find = jest.fn(organizationId => of(new HttpResponse({ body: { organizationId } })));
      mockActivatedRouteSnapshot.params = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntity5Resolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity5 = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultSaathratriEntity5).toEqual({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntity5Resolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity5 = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultSaathratriEntity5).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISaathratriEntity5>({ body: null })));
      mockActivatedRouteSnapshot.params = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntity5Resolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity5 = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultSaathratriEntity5).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
