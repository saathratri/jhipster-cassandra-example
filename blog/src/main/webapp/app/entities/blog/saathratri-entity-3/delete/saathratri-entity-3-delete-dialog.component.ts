import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
// Composite Primary Key Code
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISaathratriEntity3 } from '../saathratri-entity-3.model';
import { SaathratriEntity3Service } from '../service/saathratri-entity-3.service';

@Component({
  standalone: true,
  templateUrl: './saathratri-entity-3-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class SaathratriEntity3DeleteDialogComponent {
  saathratriEntity3?: ISaathratriEntity3;

  protected saathratriEntity3Service = inject(SaathratriEntity3Service);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(saathratriEntity3: ISaathratriEntity3): void {
    this.saathratriEntity3Service.delete(saathratriEntity3).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
