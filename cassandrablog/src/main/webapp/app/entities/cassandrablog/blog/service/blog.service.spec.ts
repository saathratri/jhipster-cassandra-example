import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { IBlog } from '../blog.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../blog.test-samples';

import { BlogService } from './blog.service';

const requireRestSample: IBlog = {
  ...sampleWithRequiredData,
};

describe('Blog Service', () => {
  let service: BlogService;
  let httpMock: HttpTestingController;
  let expectedResult: IBlog | IBlog[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(BlogService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('category', 'blogId').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Blog', () => {
      const blog = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(blog).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Blog', () => {
      const blog = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(blog).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Blog', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Blog', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Blog', () => {
      service.delete(sampleWithRequiredData).subscribe();

      const requests = httpMock.match({ method: 'DELETE' });
      expect(requests.length).toBe(1);
    });

    describe('composite-key search methods', () => {
      it('should call findAllByCompositeIdCategoryPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service.findAllByCompositeIdCategoryPageable('category').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCategoryAndCompositeIdBlogIdPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCategoryAndCompositeIdBlogIdPageable('category', 'blogId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanPageable('category', 'blogId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCategoryAndCompositeIdBlogIdLessThanEqualPageable('category', 'blogId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanPageable('category', 'blogId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable', () => {
        const returnedFromService = { ...requireRestSample };

        service
          .findAllByCompositeIdCategoryAndCompositeIdBlogIdGreaterThanEqualPageable('category', 'blogId')
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        expect(expectedResult).toMatchObject([{ ...sampleWithRequiredData }]);
      });
      it('should call findByCompositeIdCategoryAndCompositeIdBlogId', () => {
        const returnedFromService = { ...requireRestSample };

        service.findByCompositeIdCategoryAndCompositeIdBlogId('category', 'blogId').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ ...sampleWithRequiredData });
      });
    });

    describe('addBlogToCollectionIfMissing', () => {
      it('should add a Blog to an empty array', () => {
        const blog: IBlog = sampleWithRequiredData;
        expectedResult = service.addBlogToCollectionIfMissing([], blog);
        expect(expectedResult).toEqual([blog]);
      });

      it('should not add a Blog to an array that contains it', () => {
        const blog: IBlog = sampleWithRequiredData;
        const blogCollection: IBlog[] = [
          {
            ...blog,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBlogToCollectionIfMissing(blogCollection, blog);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Blog to an array that doesn't contain it", () => {
        const blog: IBlog = sampleWithRequiredData;
        const blogCollection: IBlog[] = [sampleWithPartialData];
        expectedResult = service.addBlogToCollectionIfMissing(blogCollection, blog);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(blog);
      });

      it('should add only unique Blog to an array', () => {
        const blogArray: IBlog[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const blogCollection: IBlog[] = [sampleWithRequiredData];
        expectedResult = service.addBlogToCollectionIfMissing(blogCollection, ...blogArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const blog: IBlog = sampleWithRequiredData;
        const blog2: IBlog = sampleWithPartialData;
        expectedResult = service.addBlogToCollectionIfMissing([], blog, blog2);
        expect(expectedResult).toEqual([blog, blog2]);
      });

      it('should accept null and undefined values', () => {
        const blog: IBlog = sampleWithRequiredData;
        expectedResult = service.addBlogToCollectionIfMissing([], null, blog, undefined);
        expect(expectedResult).toEqual([blog]);
      });

      it('should return initial array if no Blog is added', () => {
        const blogCollection: IBlog[] = [sampleWithRequiredData];
        expectedResult = service.addBlogToCollectionIfMissing(blogCollection, undefined, null);
        expect(expectedResult).toEqual(blogCollection);
      });
    });

    describe('compareBlog', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBlog(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = null;

        const compareResult1 = service.compareBlog(entity1, entity2);
        const compareResult2 = service.compareBlog(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithPartialData.compositeId };

        const compareResult1 = service.compareBlog(entity1, entity2);
        const compareResult2 = service.compareBlog(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return true if primaryKey matches', () => {
        const entity1 = { compositeId: sampleWithRequiredData.compositeId };
        const entity2 = { compositeId: sampleWithRequiredData.compositeId };

        const compareResult1 = service.compareBlog(entity1, entity2);
        const compareResult2 = service.compareBlog(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
