import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { MaterialModule } from 'app/shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DateTimeComponent } from 'app/components/date-time/date-time.component';

import { ISaathratriEntity5 } from '../saathratri-entity-5.model';
import { SaathratriEntity5Service } from '../service/saathratri-entity-5.service';
import { SaathratriEntity5FormGroup, SaathratriEntity5FormService } from './saathratri-entity-5-form.service';
@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-5-update',
  templateUrl: './saathratri-entity-5-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, MaterialModule, DateTimeComponent],
})
export class SaathratriEntity5UpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;
  isDateTimeValid: Record<string, boolean> = {};
  saathratriEntity5: ISaathratriEntity5 | null = null;

  protected saathratriEntity5Service = inject(SaathratriEntity5Service);
  protected saathratriEntity5FormService = inject(SaathratriEntity5FormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SaathratriEntity5FormGroup = this.saathratriEntity5FormService.createSaathratriEntity5FormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ saathratriEntity5 }) => {
      if (
        saathratriEntity5?.compositeId !== undefined &&
        saathratriEntity5.compositeId?.organizationId !== undefined &&
        saathratriEntity5.compositeId?.entityType !== undefined &&
        saathratriEntity5.compositeId?.entityId !== undefined &&
        saathratriEntity5.compositeId?.addOnId !== undefined
      ) {
        const today = dayjs().startOf('day');
      }
      this.saathratriEntity5 = saathratriEntity5;
      if (saathratriEntity5) {
        this.updateForm(saathratriEntity5);
      }
    });
  }

  previousState(): void {
    this.router.navigate(['/blog/saathratri-entity-5']);
  }

  save(): void {
    this.isSaving = true;
    const saathratriEntity5 = this.saathratriEntity5FormService.getSaathratriEntity5(this.editForm);
    if (this.isNew) {
      this.subscribeToSaveResponse(this.saathratriEntity5Service.create(saathratriEntity5));
    } else {
      this.subscribeToSaveResponse(this.saathratriEntity5Service.update(saathratriEntity5));
    }
  }

  onDateTimeValid(controlName: string, isValid: boolean): void {
    this.isDateTimeValid[controlName] = isValid;
  }

  get areAllDateTimeFieldsValid(): boolean {
    // Check if all date-time fields are valid
    return Object.values(this.isDateTimeValid).every(valid => valid);
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISaathratriEntity5>>): void {
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

  protected updateForm(saathratriEntity5: ISaathratriEntity5): void {
    this.saathratriEntity5 = saathratriEntity5;
    this.saathratriEntity5FormService.resetForm(this.editForm, saathratriEntity5);
  }
}
