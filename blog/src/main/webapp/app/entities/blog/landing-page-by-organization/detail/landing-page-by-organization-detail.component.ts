import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ILandingPageByOrganization } from '../landing-page-by-organization.model';

@Component({
  standalone: true,
  selector: 'jhi-landing-page-by-organization-detail',
  templateUrl: './landing-page-by-organization-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, ConvertFromDayjsToDateLongPipe],
})
export class LandingPageByOrganizationDetailComponent {
  landingPageByOrganization = input<ILandingPageByOrganization | null>(null);

  previousState(): void {
    window.history.back();
  }
}
