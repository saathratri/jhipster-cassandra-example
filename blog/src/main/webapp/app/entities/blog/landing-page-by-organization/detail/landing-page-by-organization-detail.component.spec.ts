import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { LandingPageByOrganizationDetailComponent } from './landing-page-by-organization-detail.component';

describe('LandingPageByOrganization Management Detail Component', () => {
  let comp: LandingPageByOrganizationDetailComponent;
  let fixture: ComponentFixture<LandingPageByOrganizationDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LandingPageByOrganizationDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./landing-page-by-organization-detail.component').then(m => m.LandingPageByOrganizationDetailComponent),
              resolve: { landingPageByOrganization: () => of({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(LandingPageByOrganizationDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LandingPageByOrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load landingPageByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LandingPageByOrganizationDetailComponent);

      // THEN
      expect(instance.landingPageByOrganization()).toEqual(
        expect.objectContaining({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' }),
      );
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
