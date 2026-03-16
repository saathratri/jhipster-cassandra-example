import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
import SharedModule from 'app/shared/shared.module';
// Single-value Primary Key Code
import { TagService } from '../service/tag.service';
import { ITag } from '../tag.model';

@Component({
  standalone: true,
  templateUrl: './tag-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
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
