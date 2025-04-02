import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IBlog } from '../blog.model';
import { BlogService } from '../service/blog.service';

import blogResolve from './blog-routing-resolve.service';

describe('Blog routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: BlogService;
  let resultBlog: IBlog | null | undefined;

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
    service = TestBed.inject(BlogService);
    resultBlog = undefined;
  });

  describe('resolve', () => {
    it('should return IBlog returned by find', () => {
      // GIVEN
      service.find = jest.fn(category => of(new HttpResponse({ body: { category } })));
      mockActivatedRouteSnapshot.params = { category: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        blogResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultBlog = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultBlog).toEqual({ category: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        blogResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultBlog = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultBlog).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IBlog>({ body: null })));
      mockActivatedRouteSnapshot.params = { category: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        blogResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultBlog = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultBlog).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
