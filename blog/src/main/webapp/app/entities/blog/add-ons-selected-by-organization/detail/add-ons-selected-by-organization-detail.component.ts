import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IAddOnsSelectedByOrganization } from '../add-ons-selected-by-organization.model';

@Component({
  standalone: true,
  selector: 'jhi-add-ons-selected-by-organization-detail',
  templateUrl: './add-ons-selected-by-organization-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, ConvertFromDayjsToDateLongPipe],
})
export class AddOnsSelectedByOrganizationDetailComponent {
  addOnsSelectedByOrganization = input<IAddOnsSelectedByOrganization | null>(null);

  previousState(): void {
    window.history.back();
  }
}
