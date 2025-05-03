import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
// Single-value Primary Key Code
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISetEntityByOrganization } from '../set-entity-by-organization.model';
import { SetEntityByOrganizationService } from '../service/set-entity-by-organization.service';

@Component({
  standalone: true,
  templateUrl: './set-entity-by-organization-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class SetEntityByOrganizationDeleteDialogComponent {
  setEntityByOrganization?: ISetEntityByOrganization;

  protected setEntityByOrganizationService = inject(SetEntityByOrganizationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.setEntityByOrganizationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
