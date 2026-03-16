import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { AddOnsAvailableByOrganizationDetail } from './add-ons-available-by-organization-detail';

describe('AddOnsAvailableByOrganization Management Detail Component', () => {
  let comp: AddOnsAvailableByOrganizationDetail;
  let fixture: ComponentFixture<AddOnsAvailableByOrganizationDetail>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./add-ons-available-by-organization-detail').then(m => m.AddOnsAvailableByOrganizationDetail),
              resolve: { addOnsAvailableByOrganization: () => of({ organizationId: 'fe29463b-6ce7-4f08-add6-0cd21f742c31' }) },
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
    fixture = TestBed.createComponent(AddOnsAvailableByOrganizationDetail);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load addOnsAvailableByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AddOnsAvailableByOrganizationDetail);

      // THEN
      expect(instance.addOnsAvailableByOrganization()).toEqual(
        expect.objectContaining({ organizationId: 'fe29463b-6ce7-4f08-add6-0cd21f742c31' }),
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
