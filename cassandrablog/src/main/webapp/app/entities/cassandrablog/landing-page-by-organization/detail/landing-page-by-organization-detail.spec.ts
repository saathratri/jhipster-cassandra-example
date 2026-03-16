import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faArrowLeft, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { TranslateModule } from '@ngx-translate/core';
import { of } from 'rxjs';

import { LandingPageByOrganizationDetail } from './landing-page-by-organization-detail';

describe('LandingPageByOrganization Management Detail Component', () => {
  let comp: LandingPageByOrganizationDetail;
  let fixture: ComponentFixture<LandingPageByOrganizationDetail>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./landing-page-by-organization-detail').then(m => m.LandingPageByOrganizationDetail),
              resolve: { landingPageByOrganization: () => of({ organizationId: '4a853e3a-594b-457b-8718-d64275e96ad2' }) },
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
    fixture = TestBed.createComponent(LandingPageByOrganizationDetail);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load landingPageByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LandingPageByOrganizationDetail);

      // THEN
      expect(instance.landingPageByOrganization()).toEqual(
        expect.objectContaining({ organizationId: '4a853e3a-594b-457b-8718-d64275e96ad2' }),
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
