import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IBlog, NewBlog } from '../blog.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { compositeId: { category: unknown; blogId: unknown } }> = Partial<Omit<T, 'compositeId'>> & {
  compositeId: T['compositeId'];
};

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBlog for edit and NewBlogFormGroupInput for create.
 */
type BlogFormGroupInput = IBlog | PartialWithRequiredKeyOf<NewBlog>;

type BlogFormDefaults = Pick<NewBlog, 'compositeId'>;

type BlogFormGroupContent = {
  compositeId: FormGroup<{
    category: FormControl<IBlog['compositeId']['category']>;
    blogId: FormControl<IBlog['compositeId']['blogId']>;
  }>;
  handle: FormControl<IBlog['handle']>;
  content: FormControl<IBlog['content']>;
};

export type BlogFormGroup = FormGroup<BlogFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BlogFormService {
  createBlogFormGroup(blog: BlogFormGroupInput = { compositeId: { category: null, blogId: null } }): BlogFormGroup {
    const blogRawValue = {
      ...this.getFormDefaults(),
      ...blog,
    };
    return new FormGroup<BlogFormGroupContent>({
      compositeId: new FormGroup({
        category: new FormControl(
          { value: blogRawValue.compositeId.category, disabled: blogRawValue.compositeId.category !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
        blogId: new FormControl(
          { value: blogRawValue.compositeId.blogId, disabled: blogRawValue.compositeId.blogId !== null },
          {
            nonNullable: true,
            validators: [Validators.required],
          },
        ),
      }),
      handle: new FormControl(blogRawValue.handle, {
        validators: [Validators.required, Validators.minLength(3)],
      }),
      content: new FormControl(blogRawValue.content, {
        validators: [Validators.required],
      }),
    });
  }

  getBlog(form: BlogFormGroup): IBlog | NewBlog {
    return form.getRawValue() as IBlog | NewBlog;
  }

  resetForm(form: BlogFormGroup, blog: BlogFormGroupInput): void {
    const blogRawValue = { ...this.getFormDefaults(), ...blog };
    form.reset(
      {
        ...blogRawValue,
        compositeId: {
          category: { value: blogRawValue.compositeId.category, disabled: true },
          blogId: { value: blogRawValue.compositeId.blogId, disabled: true },
        },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BlogFormDefaults {
    return {
      compositeId: {
        category: null,
        blogId: null,
      },
    };
  }
}
