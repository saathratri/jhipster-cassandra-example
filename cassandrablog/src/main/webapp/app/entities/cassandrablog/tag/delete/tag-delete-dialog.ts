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
import { TagService } from '../service/tag.service';
import { ITag } from '../tag.model';

@Component({
  templateUrl: './tag-delete-dialog.html',
  imports: [FontAwesomeModule, AlertError, TranslateDirective, TranslateModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class TagDeleteDialogComponent {
  tag?: ITag;

  protected tagService = inject(TagService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.tagService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
