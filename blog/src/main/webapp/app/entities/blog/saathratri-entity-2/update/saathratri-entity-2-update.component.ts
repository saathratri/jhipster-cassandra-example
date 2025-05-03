import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { MaterialModule } from 'app/shared/material.module';

import dayjs from 'dayjs/esm';
import { v4 as uuidv4 } from 'uuid'; // Import UUID (UUID v4)
import { v1 as uuidv1 } from 'uuid'; // Import TimeUUID (UUID v1)

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

  protected isResetDisabled: Record<string, boolean> = {}; // Track reset button states
  // eslint-disable-next-line @typescript-eslint/member-ordering
  private lastSavedValues: Record<string, any> = {}; // Store last valid values

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

  // Generate a new UUID and update the form
  generateUUID(field: string): void {
    const newUUID = uuidv4();
    this.editForm.get(field)?.setValue(newUUID);
    this.updateResetButtonState(field);
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
