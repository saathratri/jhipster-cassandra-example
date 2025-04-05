import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
// Composite Primary Key Code
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAddOnsSelectedByOrganization } from '../add-ons-selected-by-organization.model';
import { AddOnsSelectedByOrganizationService } from '../service/add-ons-selected-by-organization.service';

@Component({
  standalone: true,
  templateUrl: './add-ons-selected-by-organization-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class AddOnsSelectedByOrganizationDeleteDialogComponent {
  addOnsSelectedByOrganization?: IAddOnsSelectedByOrganization;

  protected addOnsSelectedByOrganizationService = inject(AddOnsSelectedByOrganizationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(addOnsSelectedByOrganization: IAddOnsSelectedByOrganization): void {
    this.addOnsSelectedByOrganizationService.delete(addOnsSelectedByOrganization).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
