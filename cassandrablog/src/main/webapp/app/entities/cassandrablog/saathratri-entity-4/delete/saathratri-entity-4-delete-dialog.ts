import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
// Composite Primary Key Code
import { ISaathratriEntity4 } from '../saathratri-entity-4.model';
import { SaathratriEntity4Service } from '../service/saathratri-entity-4.service';

@Component({
  templateUrl: './saathratri-entity-4-delete-dialog.html',
  imports: [FontAwesomeModule, AlertError, TranslateDirective, TranslateModule, FormsModule, ConvertFromDayjsToDateLongPipe],
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
