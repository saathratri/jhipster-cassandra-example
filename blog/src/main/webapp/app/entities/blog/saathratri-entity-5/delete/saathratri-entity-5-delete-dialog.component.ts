import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
// Composite Primary Key Code
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISaathratriEntity5 } from '../saathratri-entity-5.model';
import { SaathratriEntity5Service } from '../service/saathratri-entity-5.service';

@Component({
  standalone: true,
  templateUrl: './saathratri-entity-5-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class SaathratriEntity5DeleteDialogComponent {
  saathratriEntity5?: ISaathratriEntity5;

  protected saathratriEntity5Service = inject(SaathratriEntity5Service);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(saathratriEntity5: ISaathratriEntity5): void {
    this.saathratriEntity5Service.delete(saathratriEntity5).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
