import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { SetEntityByOrganizationDetail } from './set-entity-by-organization-detail';

describe('SetEntityByOrganization Management Detail Component', () => {
  let comp: SetEntityByOrganizationDetail;
  let fixture: ComponentFixture<SetEntityByOrganizationDetail>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./set-entity-by-organization-detail').then(m => m.SetEntityByOrganizationDetail),
              resolve: { setEntityByOrganization: () => of({ organizationId: '40935352-8287-4b7d-a817-6dac8e5db644' }) },
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
    fixture = TestBed.createComponent(SetEntityByOrganizationDetail);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load setEntityByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SetEntityByOrganizationDetail);

      // THEN
      expect(instance.setEntityByOrganization()).toEqual(
        expect.objectContaining({ organizationId: '40935352-8287-4b7d-a817-6dac8e5db644' }),
      );
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
