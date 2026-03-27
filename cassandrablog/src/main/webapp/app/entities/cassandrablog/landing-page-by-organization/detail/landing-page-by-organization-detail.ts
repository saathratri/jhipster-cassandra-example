import { Component, input } from '@angular/core';
import { KeyValuePipe } from '@angular/common';
import { RouterModule } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { ILandingPageByOrganization } from '../landing-page-by-organization.model';

@Component({
  selector: 'jhi-landing-page-by-organization-detail',
  templateUrl: './landing-page-by-organization-detail.html',
  imports: [
    FontAwesomeModule,
    Alert,
    AlertError,
    TranslateDirective,
    TranslateModule,
    RouterModule,
    DurationPipe,
    FormatMediumDatetimePipe,
    FormatMediumDatePipe,
    ConvertFromDayjsToDateLongPipe,
    KeyValuePipe,
  ],
})
export class LandingPageByOrganizationDetailComponent {
  landingPageByOrganization = input<ILandingPageByOrganization | null>(null);

  previousState(): void {
    window.history.back();
  }
}
