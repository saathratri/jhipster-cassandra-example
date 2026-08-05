import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { ISaathratriEntity3 } from '../saathratri-entity-3.model';

@Component({
  selector: 'jhi-saathratri-entity-3-detail',
  templateUrl: './saathratri-entity-3-detail.html',
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
export class SaathratriEntity3DetailComponent {
  saathratriEntity3 = input<ISaathratriEntity3 | null>(null);

  toArray(set: Set<string> | null | undefined): string[] {
    return set ? Array.from(set) : [];
  }

  previousState(): void {
    window.history.back();
  }
}
