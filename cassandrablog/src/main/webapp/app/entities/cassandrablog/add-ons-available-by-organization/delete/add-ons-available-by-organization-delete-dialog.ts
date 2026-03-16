import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
import SharedModule from 'app/shared/shared.module';
// Composite Primary Key Code
import { IAddOnsAvailableByOrganization } from '../add-ons-available-by-organization.model';
import { AddOnsAvailableByOrganizationService } from '../service/add-ons-available-by-organization.service';

@Component({
  standalone: true,
  templateUrl: './add-ons-available-by-organization-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class AddOnsAvailableByOrganizationDeleteDialogComponent {
  addOnsAvailableByOrganization?: IAddOnsAvailableByOrganization;

  protected addOnsAvailableByOrganizationService = inject(AddOnsAvailableByOrganizationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(addOnsAvailableByOrganization: IAddOnsAvailableByOrganization): void {
    this.addOnsAvailableByOrganizationService.delete(addOnsAvailableByOrganization).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
