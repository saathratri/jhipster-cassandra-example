import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { ISetEntityByOrganization } from '../set-entity-by-organization.model';

@Component({
  selector: 'jhi-set-entity-by-organization-detail',
  templateUrl: './set-entity-by-organization-detail.html',
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
  ],
})
export class SetEntityByOrganizationDetailComponent {
  setEntityByOrganization = input<ISetEntityByOrganization | null>(null);

  toArray(set: Set<string> | null | undefined): string[] {
    return set ? Array.from(set) : [];
  }

  previousState(): void {
    window.history.back();
  }
}
