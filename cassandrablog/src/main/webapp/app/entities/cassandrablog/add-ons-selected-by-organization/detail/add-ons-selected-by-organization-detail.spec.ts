import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { AddOnsSelectedByOrganizationDetail } from './add-ons-selected-by-organization-detail';

describe('AddOnsSelectedByOrganization Management Detail Component', () => {
  let comp: AddOnsSelectedByOrganizationDetail;
  let fixture: ComponentFixture<AddOnsSelectedByOrganizationDetail>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./add-ons-selected-by-organization-detail').then(m => m.AddOnsSelectedByOrganizationDetail),
              resolve: { addOnsSelectedByOrganization: () => of({ organizationId: '5d2569e7-278f-4572-8beb-b3497c54caf8' }) },
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
    fixture = TestBed.createComponent(AddOnsSelectedByOrganizationDetail);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load addOnsSelectedByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AddOnsSelectedByOrganizationDetail);

      // THEN
      expect(instance.addOnsSelectedByOrganization()).toEqual(
        expect.objectContaining({ organizationId: '5d2569e7-278f-4572-8beb-b3497c54caf8' }),
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
