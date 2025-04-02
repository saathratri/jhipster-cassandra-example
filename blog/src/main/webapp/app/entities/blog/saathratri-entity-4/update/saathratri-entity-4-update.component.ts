import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { MaterialModule } from 'app/shared/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISaathratriEntity4 } from '../saathratri-entity-4.model';
import { SaathratriEntity4Service } from '../service/saathratri-entity-4.service';
import { SaathratriEntity4FormGroup, SaathratriEntity4FormService } from './saathratri-entity-4-form.service';
@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-4-update',
  templateUrl: './saathratri-entity-4-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, MaterialModule],
})
export class SaathratriEntity4UpdateComponent implements OnInit {
  isSaving = false;
  // Saathratri:
  isNew = false;
  saathratriEntity4: ISaathratriEntity4 | null = null;

  protected saathratriEntity4Service = inject(SaathratriEntity4Service);
  protected saathratriEntity4FormService = inject(SaathratriEntity4FormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected router = inject(Router);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SaathratriEntity4FormGroup = this.saathratriEntity4FormService.createSaathratriEntity4FormGroup();

  ngOnInit(): void {
    this.isNew = this.activatedRoute.snapshot.routeConfig?.path === 'new' ? true : false;
    this.activatedRoute.data.subscribe(({ saathratriEntity4 }) => {
      this.saathratriEntity4 = saathratriEntity4;
      if (saathratriEntity4) {
        this.updateForm(saathratriEntity4);
      }
    });
  }

  previousState(): void {
    this.router.navigate(['/blog/saathratri-entity-4']);
  }

  save(): void {
    this.isSaving = true;
    const saathratriEntity4 = this.saathratriEntity4FormService.getSaathratriEntity4(this.editForm);
    if (this.isNew) {
      this.subscribeToSaveResponse(this.saathratriEntity4Service.create(saathratriEntity4));
    } else {
      this.subscribeToSaveResponse(this.saathratriEntity4Service.update(saathratriEntity4));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISaathratriEntity4>>): void {
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

  protected updateForm(saathratriEntity4: ISaathratriEntity4): void {
    this.saathratriEntity4 = saathratriEntity4;
    this.saathratriEntity4FormService.resetForm(this.editForm, saathratriEntity4);
  }
}
