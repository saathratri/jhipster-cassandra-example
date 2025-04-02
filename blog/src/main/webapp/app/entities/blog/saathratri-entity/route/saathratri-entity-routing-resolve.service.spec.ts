import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISaathratriEntity } from '../saathratri-entity.model';
import { SaathratriEntityService } from '../service/saathratri-entity.service';

import saathratriEntityResolve from './saathratri-entity-routing-resolve.service';

describe('SaathratriEntity routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SaathratriEntityService;
  let resultSaathratriEntity: ISaathratriEntity | null | undefined;

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
    service = TestBed.inject(SaathratriEntityService);
    resultSaathratriEntity = undefined;
  });

  describe('resolve', () => {
    it('should return ISaathratriEntity returned by find', () => {
      // GIVEN
      service.find = jest.fn(entityId => of(new HttpResponse({ body: { entityId } })));
      mockActivatedRouteSnapshot.params = { entityId: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntityResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultSaathratriEntity).toEqual({ entityId: '9fec3727-3421-4967-b213-ba36557ca194' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntityResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultSaathratriEntity).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISaathratriEntity>({ body: null })));
      mockActivatedRouteSnapshot.params = { entityId: '9fec3727-3421-4967-b213-ba36557ca194' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        saathratriEntityResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSaathratriEntity = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
      expect(resultSaathratriEntity).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
