import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { MaterialModule } from 'app/shared/material.module';

import { SetStringComponent } from 'app/components/set-string-component/set-string-component.component';

import { v4 as uuidv4 } from 'uuid'; // Import UUID (UUID v4)
import { ISetEntityByOrganization } from '../set-entity-by-organization.model';
import { SetEntityByOrganizationService } from '../service/set-entity-by-organization.service';
import { SetEntityByOrganizationFormGroup, SetEntityByOrganizationFormService } from './set-entity-by-organization-form.service';

@Component({
  standalone: true,
  selector: 'jhi-set-entity-by-organization-update',
  templateUrl: './set-entity-by-organization-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, MaterialModule, SetStringComponent],
})
export class SetEntityByOrganizationUpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;

  setEntityByOrganization: ISetEntityByOrganization | null = null;

  inputFieldsTags: Set<string> = new Set<string>(); // Start with one input field

  protected setEntityByOrganizationService = inject(SetEntityByOrganizationService);
  protected setEntityByOrganizationFormService = inject(SetEntityByOrganizationFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  protected isResetDisabled: Record<string, boolean> = {}; // Track reset button states
  // eslint-disable-next-line @typescript-eslint/member-ordering
  private lastSavedValues: Record<string, any> = {}; // Store last valid values

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SetEntityByOrganizationFormGroup = this.setEntityByOrganizationFormService.createSetEntityByOrganizationFormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ setEntityByOrganization }) => {
      this.setEntityByOrganization = setEntityByOrganization;
      if (setEntityByOrganization) {
        this.updateForm(setEntityByOrganization);
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
    this.router.navigate(['/blog/set-entity-by-organization']);
  }

  save(): void {
    this.isSaving = true;
    const setEntityByOrganization = this.setEntityByOrganizationFormService.getSetEntityByOrganization(this.editForm);

    setEntityByOrganization.tags = this.inputFieldsTags;

    // Update the last saved values when saving
    Object.keys(this.editForm.controls).forEach(field => {
      this.lastSavedValues[field] = this.editForm.get(field)?.value;
    });

    // Single-value Primary Key
    if (this.isNew) {
      this.subscribeToSaveResponse(this.setEntityByOrganizationService.create(setEntityByOrganization));
    } else {
      this.subscribeToSaveResponse(this.setEntityByOrganizationService.update(setEntityByOrganization));
    }
  }

  // Generate a new UUID and update the form
  generateUUID(field: string): void {
    const newUUID = uuidv4();
    this.editForm.get(field)?.setValue(newUUID);
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISetEntityByOrganization>>): void {
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

  protected updateForm(setEntityByOrganization: ISetEntityByOrganization): void {
    this.setEntityByOrganization = setEntityByOrganization;
    this.setEntityByOrganizationFormService.resetForm(this.editForm, setEntityByOrganization);

    this.inputFieldsTags = setEntityByOrganization.tags ? new Set(setEntityByOrganization.tags) : new Set<string>();

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
          this.updateResetButtonState(`.${nestedField}`);
        });
      } else {
        this.updateResetButtonState(field);
      }
    });
  }
}
