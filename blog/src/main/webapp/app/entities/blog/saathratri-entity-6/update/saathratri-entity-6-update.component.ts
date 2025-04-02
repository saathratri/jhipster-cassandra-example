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

import { ISaathratriEntity6 } from '../saathratri-entity-6.model';
import { SaathratriEntity6Service } from '../service/saathratri-entity-6.service';
import { SaathratriEntity6FormGroup, SaathratriEntity6FormService } from './saathratri-entity-6-form.service';
@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-6-update',
  templateUrl: './saathratri-entity-6-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, MaterialModule, DateTimeComponent],
})
export class SaathratriEntity6UpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;
  isDateTimeValid: Record<string, boolean> = {};
  saathratriEntity6: ISaathratriEntity6 | null = null;

  protected saathratriEntity6Service = inject(SaathratriEntity6Service);
  protected saathratriEntity6FormService = inject(SaathratriEntity6FormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SaathratriEntity6FormGroup = this.saathratriEntity6FormService.createSaathratriEntity6FormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ saathratriEntity6 }) => {
      if (
        saathratriEntity6?.compositeId !== undefined &&
        saathratriEntity6.compositeId?.organizationId !== undefined &&
        saathratriEntity6.compositeId?.arrivalDate !== undefined &&
        saathratriEntity6.compositeId?.accountNumber !== undefined &&
        saathratriEntity6.compositeId?.createdTimeId !== undefined
      ) {
        const today = dayjs().startOf('day');
      }
      this.saathratriEntity6 = saathratriEntity6;
      if (saathratriEntity6) {
        this.updateForm(saathratriEntity6);
      }
    });
  }

  previousState(): void {
    this.router.navigate(['/blog/saathratri-entity-6']);
  }

  save(): void {
    this.isSaving = true;
    const saathratriEntity6 = this.saathratriEntity6FormService.getSaathratriEntity6(this.editForm);
    if (this.isNew) {
      this.subscribeToSaveResponse(this.saathratriEntity6Service.create(saathratriEntity6));
    } else {
      this.subscribeToSaveResponse(this.saathratriEntity6Service.update(saathratriEntity6));
    }
  }

  onDateTimeValid(controlName: string, isValid: boolean): void {
    this.isDateTimeValid[controlName] = isValid;
  }

  get areAllDateTimeFieldsValid(): boolean {
    // Check if all date-time fields are valid
    return Object.values(this.isDateTimeValid).every(valid => valid);
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISaathratriEntity6>>): void {
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

  protected updateForm(saathratriEntity6: ISaathratriEntity6): void {
    this.saathratriEntity6 = saathratriEntity6;
    this.saathratriEntity6FormService.resetForm(this.editForm, saathratriEntity6);
  }
}
