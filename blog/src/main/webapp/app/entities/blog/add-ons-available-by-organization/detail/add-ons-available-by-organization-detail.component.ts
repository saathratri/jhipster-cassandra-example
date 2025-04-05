import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IAddOnsAvailableByOrganization } from '../add-ons-available-by-organization.model';

@Component({
  standalone: true,
  selector: 'jhi-add-ons-available-by-organization-detail',
  templateUrl: './add-ons-available-by-organization-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, ConvertFromDayjsToDateLongPipe],
})
export class AddOnsAvailableByOrganizationDetailComponent {
  addOnsAvailableByOrganization = input<IAddOnsAvailableByOrganization | null>(null);

  previousState(): void {
    window.history.back();
  }
}
