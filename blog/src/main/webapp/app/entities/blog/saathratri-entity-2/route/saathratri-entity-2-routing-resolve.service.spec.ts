import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISaathratriEntity2 } from '../saathratri-entity-2.model';
import { SaathratriEntity2Service } from '../service/saathratri-entity-2.service';

import saathratriEntity2Resolve from './saathratri-entity-2-routing-resolve.service';

describe('SaathratriEntity2 routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SaathratriEntity2Service;
  let resultSaathratriEntity2: ISaathratriEntity2 | null | undefined;

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
    service = TestBed.inject(SaathratriEntity2Service);
    resultSaathratriEntity2 = undefined;
  });

  describe('resolve', () => {
    it('should return ISaathratriEntity2 returned by find', () => {
      // GIVEN
      service.find = jest.fn(entityTypeId => of(new HttpResponse({ body: { entityTypeId } })));
      mockActivatedRouteSnapshot.params = { entityTypeId: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntity2Resolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity2 = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultSaathratriEntity2).toEqual({ entityTypeId: '9fec3727-3421-4967-b213-ba36557ca194' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntity2Resolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity2 = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultSaathratriEntity2).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISaathratriEntity2>({ body: null })));
      mockActivatedRouteSnapshot.params = { entityTypeId: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntity2Resolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity2 = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultSaathratriEntity2).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
