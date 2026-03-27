import { Component, input } from '@angular/core';
import { KeyValuePipe } from '@angular/common';
import { RouterModule } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { IAddOnsAvailableByOrganization } from '../add-ons-available-by-organization.model';

@Component({
  selector: 'jhi-add-ons-available-by-organization-detail',
  templateUrl: './add-ons-available-by-organization-detail.html',
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
export class AddOnsAvailableByOrganizationDetailComponent {
  addOnsAvailableByOrganization = input<IAddOnsAvailableByOrganization | null>(null);

  previousState(): void {
    window.history.back();
  }
}
