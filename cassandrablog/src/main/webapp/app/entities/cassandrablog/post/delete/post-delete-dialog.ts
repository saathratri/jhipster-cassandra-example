import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
import SharedModule from 'app/shared/shared.module';
// Composite Primary Key Code
import { IPost } from '../post.model';
import { PostService } from '../service/post.service';

@Component({
  standalone: true,
  templateUrl: './post-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class PostDeleteDialogComponent {
  post?: IPost;

  protected postService = inject(PostService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(post: IPost): void {
    this.postService.delete(post).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
