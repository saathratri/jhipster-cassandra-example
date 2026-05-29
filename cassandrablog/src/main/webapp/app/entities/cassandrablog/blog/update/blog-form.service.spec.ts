import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../blog.test-samples';

import { BlogFormService } from './blog-form.service';

describe('Blog Form Service', () => {
  let service: BlogFormService;

  beforeEach(() => {
    service = TestBed.inject(BlogFormService);
  });

  describe('Service methods', () => {
    describe('createBlogFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBlogFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            handle: expect.any(Object),
            content: expect.any(Object),
          }),
        );
      });

      it('passing IBlog should create a new form with FormGroup', () => {
        const formGroup = service.createBlogFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            handle: expect.any(Object),
            content: expect.any(Object),
          }),
        );
      });
    });

    describe('getBlog', () => {
      it('should return NewBlog for default Blog initial value', () => {
        const formGroup = service.createBlogFormGroup(sampleWithNewData);

        const blog = service.getBlog(formGroup);

        expect(blog).toMatchObject(sampleWithNewData);
      });

      it('should return NewBlog for empty Blog initial value', () => {
        const formGroup = service.createBlogFormGroup();

        const blog = service.getBlog(formGroup);

        expect(blog).toMatchObject({});
      });

      it('should return IBlog', () => {
        const formGroup = service.createBlogFormGroup(sampleWithRequiredData);

        const blog = service.getBlog(formGroup);

        expect(blog).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBlog should keep the key control disabled', () => {
        const formGroup = service.createBlogFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.compositeId.controls.category.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.compositeId.controls.category.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createBlogFormGroup();

        service.resetForm(formGroup, { compositeId: { category: null, blogId: null } });

        expect(formGroup.controls.compositeId.controls.category.disabled).toBe(true);
      });
    });
  });
});
