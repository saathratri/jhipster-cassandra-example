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
              resolve: { addOnsSelectedByOrganization: () => of({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' }) },
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
    it('Should load addOnsSelectedByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AddOnsSelectedByOrganizationDetailComponent);

      // THEN
      expect(instance.addOnsSelectedByOrganization()).toEqual(
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
