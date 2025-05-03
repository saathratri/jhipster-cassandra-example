import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AddOnsAvailableByOrganizationDetailComponent } from './add-ons-available-by-organization-detail.component';

describe('AddOnsAvailableByOrganization Management Detail Component', () => {
  let comp: AddOnsAvailableByOrganizationDetailComponent;
  let fixture: ComponentFixture<AddOnsAvailableByOrganizationDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddOnsAvailableByOrganizationDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./add-ons-available-by-organization-detail.component').then(m => m.AddOnsAvailableByOrganizationDetailComponent),
              resolve: { addOnsAvailableByOrganization: () => of({ organizationId: 'fe29463b-6ce7-4f08-add6-0cd21f742c31' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AddOnsAvailableByOrganizationDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddOnsAvailableByOrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load addOnsAvailableByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AddOnsAvailableByOrganizationDetailComponent);

      // THEN
      expect(instance.addOnsAvailableByOrganization()).toEqual(
        expect.objectContaining({ organizationId: 'fe29463b-6ce7-4f08-add6-0cd21f742c31' }),
      );
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
