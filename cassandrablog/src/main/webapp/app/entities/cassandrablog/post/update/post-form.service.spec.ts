import { beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../post.test-samples';

import { PostFormService } from './post-form.service';

describe('Post Form Service', () => {
  let service: PostFormService;

  beforeEach(() => {
    service = TestBed.inject(PostFormService);
  });

  describe('Service methods', () => {
    describe('createPostFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPostFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            publishedDateTime: expect.any(Object),
            sentDate: expect.any(Object),
          }),
        );
      });

      it('passing IPost should create a new form with FormGroup', () => {
        const formGroup = service.createPostFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            compositeId: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            publishedDateTime: expect.any(Object),
            sentDate: expect.any(Object),
          }),
        );
      });
    });

    describe('getPost', () => {
      it('should return NewPost for default Post initial value', () => {
        const formGroup = service.createPostFormGroup(sampleWithNewData);

        const post = service.getPost(formGroup);

        expect(post).toMatchObject(sampleWithNewData);
      });

      it('should return NewPost for empty Post initial value', () => {
        const formGroup = service.createPostFormGroup();

        const post = service.getPost(formGroup);

        expect(post).toMatchObject({});
      });

      it('should return IPost', () => {
        const formGroup = service.createPostFormGroup(sampleWithRequiredData);

        const post = service.getPost(formGroup);

        expect(post).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPost should keep the key control disabled', () => {
        const formGroup = service.createPostFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.compositeId.controls.createdDate.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.compositeId.controls.createdDate.disabled).toBe(true);
      });

      it('resetForm disables the key control even for a new entity', () => {
        const formGroup = service.createPostFormGroup();

        service.resetForm(formGroup, { compositeId: { createdDate: null, addedDateTime: null, postId: null } });

        expect(formGroup.controls.compositeId.controls.createdDate.disabled).toBe(true);
      });
    });
  });
});
