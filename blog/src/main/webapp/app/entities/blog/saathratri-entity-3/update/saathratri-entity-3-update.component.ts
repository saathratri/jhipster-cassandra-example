import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { MaterialModule } from 'app/shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { ISaathratriEntity3 } from '../saathratri-entity-3.model';
import { SaathratriEntity3Service } from '../service/saathratri-entity-3.service';
import { SaathratriEntity3FormGroup, SaathratriEntity3FormService } from './saathratri-entity-3-form.service';
@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-3-update',
  templateUrl: './saathratri-entity-3-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, MaterialModule],
})
export class SaathratriEntity3UpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;
  saathratriEntity3: ISaathratriEntity3 | null = null;

  protected saathratriEntity3Service = inject(SaathratriEntity3Service);
  protected saathratriEntity3FormService = inject(SaathratriEntity3FormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SaathratriEntity3FormGroup = this.saathratriEntity3FormService.createSaathratriEntity3FormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ saathratriEntity3 }) => {
      if (
        saathratriEntity3?.compositeId !== undefined &&
        saathratriEntity3.compositeId?.entityType !== undefined &&
        saathratriEntity3.compositeId?.createdTimeId !== undefined
      ) {
        const today = dayjs().startOf('day');
      }
      this.saathratriEntity3 = saathratriEntity3;
      if (saathratriEntity3) {
        this.updateForm(saathratriEntity3);
      }
    });
  }

  previousState(): void {
    this.router.navigate(['/blog/saathratri-entity-3']);
  }

  save(): void {
    this.isSaving = true;
    const saathratriEntity3 = this.saathratriEntity3FormService.getSaathratriEntity3(this.editForm);
    if (this.isNew) {
      this.subscribeToSaveResponse(this.saathratriEntity3Service.create(saathratriEntity3));
    } else {
      this.subscribeToSaveResponse(this.saathratriEntity3Service.update(saathratriEntity3));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISaathratriEntity3>>): void {
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

  protected updateForm(saathratriEntity3: ISaathratriEntity3): void {
    this.saathratriEntity3 = saathratriEntity3;
    this.saathratriEntity3FormService.resetForm(this.editForm, saathratriEntity3);
  }
}
