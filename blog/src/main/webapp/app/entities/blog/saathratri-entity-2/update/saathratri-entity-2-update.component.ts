import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { MaterialModule } from 'app/shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import dayjs from 'dayjs/esm';

import { ISaathratriEntity2 } from '../saathratri-entity-2.model';
import { SaathratriEntity2Service } from '../service/saathratri-entity-2.service';
import { SaathratriEntity2FormGroup, SaathratriEntity2FormService } from './saathratri-entity-2-form.service';
@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-2-update',
  templateUrl: './saathratri-entity-2-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, MaterialModule],
})
export class SaathratriEntity2UpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;
  saathratriEntity2: ISaathratriEntity2 | null = null;

  protected saathratriEntity2Service = inject(SaathratriEntity2Service);
  protected saathratriEntity2FormService = inject(SaathratriEntity2FormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SaathratriEntity2FormGroup = this.saathratriEntity2FormService.createSaathratriEntity2FormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ saathratriEntity2 }) => {
      if (
        saathratriEntity2?.compositeId !== undefined &&
        saathratriEntity2.compositeId?.entityTypeId !== undefined &&
        saathratriEntity2.compositeId?.yearOfDateAdded !== undefined &&
        saathratriEntity2.compositeId?.arrivalDate !== undefined &&
        saathratriEntity2.compositeId?.blogId !== undefined
      ) {
        const today = dayjs().startOf('day');
      }
      this.saathratriEntity2 = saathratriEntity2;
      if (saathratriEntity2) {
        this.updateForm(saathratriEntity2);
      }
    });
  }

  previousState(): void {
    this.router.navigate(['/blog/saathratri-entity-2']);
  }

  save(): void {
    this.isSaving = true;
    const saathratriEntity2 = this.saathratriEntity2FormService.getSaathratriEntity2(this.editForm);
    if (this.isNew) {
      this.subscribeToSaveResponse(this.saathratriEntity2Service.create(saathratriEntity2));
    } else {
      this.subscribeToSaveResponse(this.saathratriEntity2Service.update(saathratriEntity2));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISaathratriEntity2>>): void {
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

  protected updateForm(saathratriEntity2: ISaathratriEntity2): void {
    this.saathratriEntity2 = saathratriEntity2;
    this.saathratriEntity2FormService.resetForm(this.editForm, saathratriEntity2);
  }
}
