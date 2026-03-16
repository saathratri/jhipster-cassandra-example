import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
import SharedModule from 'app/shared/shared.module';
// Single-value Primary Key Code
import { TajUserService } from '../service/taj-user.service';
import { ITajUser } from '../taj-user.model';

@Component({
  standalone: true,
  templateUrl: './taj-user-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
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
