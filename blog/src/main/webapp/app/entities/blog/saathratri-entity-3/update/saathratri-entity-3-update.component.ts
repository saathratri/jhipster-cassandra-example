import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { MaterialModule } from 'app/shared/material.module';

import dayjs from 'dayjs/esm';
import { SetComponent } from 'app/components/set-component/set-component.component';
import { v1 as uuidv1 } from 'uuid'; // Import TimeUUID (UUID v1)

import { ISaathratriEntity3 } from '../saathratri-entity-3.model';
import { SaathratriEntity3Service } from '../service/saathratri-entity-3.service';
import { SaathratriEntity3FormGroup, SaathratriEntity3FormService } from './saathratri-entity-3-form.service';

@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-3-update',
  templateUrl: './saathratri-entity-3-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, MaterialModule, SetComponent],
})
export class SaathratriEntity3UpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;

  saathratriEntity3: ISaathratriEntity3 | null = null;

  inputFieldsTags: Set<string> = new Set<string>(); // Start with one input field

  protected saathratriEntity3Service = inject(SaathratriEntity3Service);
  protected saathratriEntity3FormService = inject(SaathratriEntity3FormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  protected isResetDisabled: Record<string, boolean> = {}; // Track reset button states
  // eslint-disable-next-line @typescript-eslint/member-ordering
  private lastSavedValues: Record<string, any> = {}; // Store last valid values

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
      } else {
        this.initializeResetButtonStates();
      }
    });

    // Listen for changes to enable/disable reset button
    Object.keys(this.editForm.controls).forEach(field => {
      this.editForm.get(field)?.valueChanges.subscribe(() => {
        this.isResetDisabled[field] = true; // Disable reset button on load
        this.updateResetButtonState(field);
      });
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

  // Generate a new TimeUUID and update the form
  generateTimeUUID(field: string): void {
    const newTimeUUID = uuidv1();
    this.editForm.get(field)?.setValue(newTimeUUID);
    this.updateResetButtonState(field);
  }

  // Clear the TimeUUID field
  reset(field: string): void {
    const lastValue = this.lastSavedValues[field];
    const currentValue = this.editForm.get(field)?.value;

    // Only reset if the value has changed
    if (currentValue !== lastValue) {
      this.editForm.get(field)?.setValue(lastValue, { emitEvent: false });
    }

    // Ensure reset button gets disabled after restoring the previous value
    this.updateResetButtonState(field);
  }

  updateResetButtonState(field: string): void {
    const lastValue = this.lastSavedValues[field];
    const currentValue = this.editForm.get(field)?.value;

    if (currentValue === null) {
      this.isResetDisabled[field] = true; // Disable if null
    } else {
      this.isResetDisabled[field] = currentValue === lastValue; // Disable if unchanged
    }
  }

  handleTagsInputChange(updatedFields: Set<string>): void {
    this.inputFieldsTags = new Set(updatedFields); // Update parent data when child component emits changes
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

    Object.keys(this.editForm.controls).forEach(field => {
      this.lastSavedValues[field] = this.editForm.get(field)?.value;
    });
  }

  protected initializeResetButtonStates(): void {
    Object.keys(this.editForm.controls).forEach(field => {
      const control = this.editForm.get(field);

      // Handle nested composite keys
      if (control instanceof FormGroup) {
        Object.keys(control.controls).forEach(nestedField => {
          this.updateResetButtonState(`compositeId.${nestedField}`);
        });
      } else {
        this.updateResetButtonState(field);
      }
    });
  }
}
