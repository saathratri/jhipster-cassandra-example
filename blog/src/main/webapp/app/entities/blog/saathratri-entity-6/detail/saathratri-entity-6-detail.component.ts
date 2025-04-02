import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISaathratriEntity6 } from '../saathratri-entity-6.model';

@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-6-detail',
  templateUrl: './saathratri-entity-6-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, ConvertFromDayjsToDateLongPipe],
})
export class SaathratriEntity6DetailComponent {
  saathratriEntity6 = input<ISaathratriEntity6 | null>(null);

  previousState(): void {
    window.history.back();
  }
}
