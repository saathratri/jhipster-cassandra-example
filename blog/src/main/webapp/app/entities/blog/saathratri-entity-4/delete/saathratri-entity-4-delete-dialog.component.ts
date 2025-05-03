import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
// Composite Primary Key Code
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISaathratriEntity4 } from '../saathratri-entity-4.model';
import { SaathratriEntity4Service } from '../service/saathratri-entity-4.service';

@Component({
  standalone: true,
  templateUrl: './saathratri-entity-4-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class SaathratriEntity4DeleteDialogComponent {
  saathratriEntity4?: ISaathratriEntity4;

  protected saathratriEntity4Service = inject(SaathratriEntity4Service);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(saathratriEntity4: ISaathratriEntity4): void {
    this.saathratriEntity4Service.delete(saathratriEntity4).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
