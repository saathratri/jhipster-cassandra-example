import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISaathratriEntity2 } from '../saathratri-entity-2.model';

@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-2-detail',
  templateUrl: './saathratri-entity-2-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, ConvertFromDayjsToDateLongPipe],
})
export class SaathratriEntity2DetailComponent {
  saathratriEntity2 = input<ISaathratriEntity2 | null>(null);

  previousState(): void {
    window.history.back();
  }
}
