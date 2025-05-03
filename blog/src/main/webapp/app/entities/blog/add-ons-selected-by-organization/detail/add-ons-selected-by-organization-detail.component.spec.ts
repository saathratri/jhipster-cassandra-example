import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AddOnsSelectedByOrganizationDetailComponent } from './add-ons-selected-by-organization-detail.component';

describe('AddOnsSelectedByOrganization Management Detail Component', () => {
  let comp: AddOnsSelectedByOrganizationDetailComponent;
  let fixture: ComponentFixture<AddOnsSelectedByOrganizationDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddOnsSelectedByOrganizationDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./add-ons-selected-by-organization-detail.component').then(m => m.AddOnsSelectedByOrganizationDetailComponent),
              resolve: { addOnsSelectedByOrganization: () => of({ organizationId: '5d2569e7-278f-4572-8beb-b3497c54caf8' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AddOnsSelectedByOrganizationDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddOnsSelectedByOrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load addOnsSelectedByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AddOnsSelectedByOrganizationDetailComponent);

      // THEN
      expect(instance.addOnsSelectedByOrganization()).toEqual(
        expect.objectContaining({ organizationId: '5d2569e7-278f-4572-8beb-b3497c54caf8' }),
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
