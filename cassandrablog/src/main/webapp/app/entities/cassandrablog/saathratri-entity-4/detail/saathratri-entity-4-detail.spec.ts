import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { SaathratriEntity4Detail } from './saathratri-entity-4-detail';

describe('SaathratriEntity4 Management Detail Component', () => {
  let comp: SaathratriEntity4Detail;
  let fixture: ComponentFixture<SaathratriEntity4Detail>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./saathratri-entity-4-detail').then(m => m.SaathratriEntity4Detail),
              resolve: { saathratriEntity4: () => of({ organizationId: 'f84082d7-31a6-42c6-a2c0-915e5221b32b' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    });
    const library = TestBed.inject(FaIconLibrary);
    library.addIcons(faArrowLeft);
    library.addIcons(faPencilAlt);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaathratriEntity4Detail);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load saathratriEntity4 on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SaathratriEntity4Detail);

      // THEN
      expect(instance.saathratriEntity4()).toEqual(expect.objectContaining({ organizationId: 'f84082d7-31a6-42c6-a2c0-915e5221b32b' }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      vitest.spyOn(globalThis.history, 'back');
      comp.previousState();
      expect(globalThis.history.back).toHaveBeenCalled();
    });
  });
});
