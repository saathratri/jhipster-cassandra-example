import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { SaathratriEntity2Detail } from './saathratri-entity-2-detail';

describe('SaathratriEntity2 Management Detail Component', () => {
  let comp: SaathratriEntity2Detail;
  let fixture: ComponentFixture<SaathratriEntity2Detail>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./saathratri-entity-2-detail').then(m => m.SaathratriEntity2Detail),
              resolve: { saathratriEntity2: () => of({ entityTypeId: '478effd9-630b-455d-b46d-efa51cd24482' }) },
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
    fixture = TestBed.createComponent(SaathratriEntity2Detail);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load saathratriEntity2 on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SaathratriEntity2Detail);

      // THEN
      expect(instance.saathratriEntity2()).toEqual(expect.objectContaining({ entityTypeId: '478effd9-630b-455d-b46d-efa51cd24482' }));
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
