import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISaathratriEntity4 } from '../saathratri-entity-4.model';

@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-4-detail',
  templateUrl: './saathratri-entity-4-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, ConvertFromDayjsToDateLongPipe],
})
export class SaathratriEntity4DetailComponent {
  saathratriEntity4 = input<ISaathratriEntity4 | null>(null);

  previousState(): void {
    window.history.back();
  }
}
