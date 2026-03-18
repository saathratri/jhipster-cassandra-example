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
import { ISaathratriEntity3 } from '../saathratri-entity-3.model';
import { SaathratriEntity3Service } from '../service/saathratri-entity-3.service';

@Component({
  templateUrl: './saathratri-entity-3-delete-dialog.html',
  imports: [FontAwesomeModule, AlertError, TranslateDirective, TranslateModule, FormsModule, ConvertFromDayjsToDateLongPipe],
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
