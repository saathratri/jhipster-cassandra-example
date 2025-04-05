import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe } from 'app/shared/date';
// Single-value Primary Key Code
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ILandingPageByOrganization } from '../landing-page-by-organization.model';
import { LandingPageByOrganizationService } from '../service/landing-page-by-organization.service';

@Component({
  standalone: true,
  templateUrl: './landing-page-by-organization-delete-dialog.component.html',
  imports: [SharedModule, FormsModule, ConvertFromDayjsToDateLongPipe],
})
export class LandingPageByOrganizationDeleteDialogComponent {
  landingPageByOrganization?: ILandingPageByOrganization;

  protected landingPageByOrganizationService = inject(LandingPageByOrganizationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.landingPageByOrganizationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
