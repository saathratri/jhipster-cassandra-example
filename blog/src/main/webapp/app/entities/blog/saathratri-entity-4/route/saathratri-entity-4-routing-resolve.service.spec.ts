import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISaathratriEntity4 } from '../saathratri-entity-4.model';
import { SaathratriEntity4Service } from '../service/saathratri-entity-4.service';

import saathratriEntity4Resolve from './saathratri-entity-4-routing-resolve.service';

describe('SaathratriEntity4 routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SaathratriEntity4Service;
  let resultSaathratriEntity4: ISaathratriEntity4 | null | undefined;

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
    service = TestBed.inject(SaathratriEntity4Service);
    resultSaathratriEntity4 = undefined;
  });

  describe('resolve', () => {
    it('should return ISaathratriEntity4 returned by find', () => {
      // GIVEN
      service.find = jest.fn(organizationId => of(new HttpResponse({ body: { organizationId } })));
      mockActivatedRouteSnapshot.params = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntity4Resolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity4 = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultSaathratriEntity4).toEqual({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntity4Resolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity4 = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultSaathratriEntity4).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISaathratriEntity4>({ body: null })));
      mockActivatedRouteSnapshot.params = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntity4Resolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity4 = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultSaathratriEntity4).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
