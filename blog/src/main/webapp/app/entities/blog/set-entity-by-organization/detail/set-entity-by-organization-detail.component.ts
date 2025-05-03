import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISetEntityByOrganization } from '../set-entity-by-organization.model';

@Component({
  standalone: true,
  selector: 'jhi-set-entity-by-organization-detail',
  templateUrl: './set-entity-by-organization-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, ConvertFromDayjsToDateLongPipe],
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
