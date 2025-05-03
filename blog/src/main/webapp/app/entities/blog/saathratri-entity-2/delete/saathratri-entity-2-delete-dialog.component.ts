import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
// Composite Primary Key Code
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISaathratriEntity2 } from '../saathratri-entity-2.model';
import { SaathratriEntity2Service } from '../service/saathratri-entity-2.service';

@Component({
  standalone: true,
  templateUrl: './saathratri-entity-2-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class SaathratriEntity2DeleteDialogComponent {
  saathratriEntity2?: ISaathratriEntity2;

  protected saathratriEntity2Service = inject(SaathratriEntity2Service);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(saathratriEntity2: ISaathratriEntity2): void {
    this.saathratriEntity2Service.delete(saathratriEntity2).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
