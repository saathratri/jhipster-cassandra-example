import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
// Single-value Primary Key Code
import { TajUserService } from '../service/taj-user.service';
import { ITajUser } from '../taj-user.model';

@Component({
  templateUrl: './taj-user-delete-dialog.html',
  imports: [FontAwesomeModule, AlertError, TranslateDirective, TranslateModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class TajUserDeleteDialogComponent {
  tajUser?: ITajUser;

  protected tajUserService = inject(TajUserService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.tajUserService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
