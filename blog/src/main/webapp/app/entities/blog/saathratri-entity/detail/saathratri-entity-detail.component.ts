import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISaathratriEntity } from '../saathratri-entity.model';

@Component({
  standalone: true,
  selector: 'jhi-saathratri-entity-detail',
  templateUrl: './saathratri-entity-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, ConvertFromDayjsToDateLongPipe],
})
export class SaathratriEntityDetailComponent {
  saathratriEntity = input<ISaathratriEntity | null>(null);

  previousState(): void {
    window.history.back();
  }
}
