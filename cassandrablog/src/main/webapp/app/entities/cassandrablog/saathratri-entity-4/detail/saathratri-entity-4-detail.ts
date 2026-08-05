/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';

import { Alert } from 'app/shared/alert/alert';
import { AlertError } from 'app/shared/alert/alert-error';
import { ConvertFromDayjsToDateLongPipe, DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { TranslateDirective } from 'app/shared/language';
import { ISaathratriEntity4 } from '../saathratri-entity-4.model';

@Component({
  selector: 'jhi-saathratri-entity-4-detail',
  templateUrl: './saathratri-entity-4-detail.html',
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
export class SaathratriEntity4DetailComponent {
  saathratriEntity4 = input<ISaathratriEntity4 | null>(null);

  previousState(): void {
    window.history.back();
  }
}
