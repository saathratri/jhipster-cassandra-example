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
              resolve: { addOnsAvailableByOrganization: () => of({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' }) },
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
    it('Should load addOnsAvailableByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AddOnsAvailableByOrganizationDetailComponent);

      // THEN
      expect(instance.addOnsAvailableByOrganization()).toEqual(
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
