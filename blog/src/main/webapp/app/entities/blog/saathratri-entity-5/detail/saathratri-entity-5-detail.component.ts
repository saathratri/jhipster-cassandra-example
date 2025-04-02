import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISaathratriEntity5 } from '../saathratri-entity-5.model';

@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-5-detail',
  templateUrl: './saathratri-entity-5-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, ConvertFromDayjsToDateLongPipe],
})
export class SaathratriEntity5DetailComponent {
  saathratriEntity5 = input<ISaathratriEntity5 | null>(null);

  previousState(): void {
    window.history.back();
  }
}
