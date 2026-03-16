import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
import SharedModule from 'app/shared/shared.module';
// Composite Primary Key Code
import { IBlog } from '../blog.model';
import { BlogService } from '../service/blog.service';

@Component({
  standalone: true,
  templateUrl: './blog-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class BlogDeleteDialogComponent {
  blog?: IBlog;

  protected blogService = inject(BlogService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(blog: IBlog): void {
    this.blogService.delete(blog).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
