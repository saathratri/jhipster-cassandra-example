import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { MaterialModule } from 'app/shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITag } from '../tag.model';
import { TagService } from '../service/tag.service';
import { TagFormGroup, TagFormService } from './tag-form.service';
@Component({
  standalone: true,
  selector: 'jhi-tag-update',
  templateUrl: './tag-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, MaterialModule],
})
export class TagUpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;
  tag: ITag | null = null;

  protected tagService = inject(TagService);
  protected tagFormService = inject(TagFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TagFormGroup = this.tagFormService.createTagFormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ tag }) => {
      this.tag = tag;
      if (tag) {
        this.updateForm(tag);
      }
    });
  }

  previousState(): void {
    this.router.navigate(['/blog/tag']);
  }

  save(): void {
    this.isSaving = true;
    const tag = this.tagFormService.getTag(this.editForm);
    // Single-value Primary Key
    if (this.isNew) {
      this.subscribeToSaveResponse(this.tagService.create(tag));
    } else {
      this.subscribeToSaveResponse(this.tagService.update(tag));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITag>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(tag: ITag): void {
    this.tag = tag;
    this.tagFormService.resetForm(this.editForm, tag);
  }
}
