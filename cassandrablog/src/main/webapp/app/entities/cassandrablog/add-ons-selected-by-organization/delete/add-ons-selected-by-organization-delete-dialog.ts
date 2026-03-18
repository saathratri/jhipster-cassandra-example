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
import { IAddOnsSelectedByOrganization } from '../add-ons-selected-by-organization.model';
import { AddOnsSelectedByOrganizationService } from '../service/add-ons-selected-by-organization.service';

@Component({
  templateUrl: './add-ons-selected-by-organization-delete-dialog.html',
  imports: [FontAwesomeModule, AlertError, TranslateDirective, TranslateModule, FormsModule, ConvertFromDayjsToDateLongPipe],
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
