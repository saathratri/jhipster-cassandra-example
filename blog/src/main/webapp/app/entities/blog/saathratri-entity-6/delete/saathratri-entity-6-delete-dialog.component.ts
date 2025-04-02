import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
// Composite Primary Key Code
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISaathratriEntity6 } from '../saathratri-entity-6.model';
import { SaathratriEntity6Service } from '../service/saathratri-entity-6.service';

@Component({
  standalone: true,
  templateUrl: './saathratri-entity-6-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class SaathratriEntity6DeleteDialogComponent {
  saathratriEntity6?: ISaathratriEntity6;

  protected saathratriEntity6Service = inject(SaathratriEntity6Service);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(saathratriEntity6: ISaathratriEntity6): void {
    this.saathratriEntity6Service.delete(saathratriEntity6).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
