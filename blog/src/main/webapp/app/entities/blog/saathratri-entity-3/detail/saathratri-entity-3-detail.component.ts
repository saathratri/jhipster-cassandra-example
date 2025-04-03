import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISaathratriEntity3 } from '../saathratri-entity-3.model';

@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-3-detail',
  templateUrl: './saathratri-entity-3-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, ConvertFromDayjsToDateLongPipe],
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
