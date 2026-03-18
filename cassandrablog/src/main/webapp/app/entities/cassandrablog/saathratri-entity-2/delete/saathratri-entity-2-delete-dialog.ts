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
import { ISaathratriEntity2 } from '../saathratri-entity-2.model';
import { SaathratriEntity2Service } from '../service/saathratri-entity-2.service';

@Component({
  templateUrl: './saathratri-entity-2-delete-dialog.html',
  imports: [FontAwesomeModule, AlertError, TranslateDirective, TranslateModule, FormsModule, ConvertFromDayjsToDateLongPipe],
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
