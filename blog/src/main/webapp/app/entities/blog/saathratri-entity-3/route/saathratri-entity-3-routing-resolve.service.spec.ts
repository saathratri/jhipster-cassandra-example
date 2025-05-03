import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISaathratriEntity3 } from '../saathratri-entity-3.model';
import { SaathratriEntity3Service } from '../service/saathratri-entity-3.service';

import saathratriEntity3Resolve from './saathratri-entity-3-routing-resolve.service';

describe('SaathratriEntity3 routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SaathratriEntity3Service;
  let resultSaathratriEntity3: ISaathratriEntity3 | null | undefined;

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
    service = TestBed.inject(SaathratriEntity3Service);
    resultSaathratriEntity3 = undefined;
  });

  describe('resolve', () => {
    it('should return ISaathratriEntity3 returned by find', () => {
      // GIVEN
      service.find = jest.fn(entityType => of(new HttpResponse({ body: { entityType } })));
      mockActivatedRouteSnapshot.params = { entityType: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntity3Resolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity3 = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultSaathratriEntity3).toEqual({ entityType: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntity3Resolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity3 = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultSaathratriEntity3).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISaathratriEntity3>({ body: null })));
      mockActivatedRouteSnapshot.params = { entityType: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntity3Resolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity3 = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultSaathratriEntity3).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
