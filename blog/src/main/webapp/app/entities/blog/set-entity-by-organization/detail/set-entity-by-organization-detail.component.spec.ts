import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SetEntityByOrganizationDetailComponent } from './set-entity-by-organization-detail.component';

describe('SetEntityByOrganization Management Detail Component', () => {
  let comp: SetEntityByOrganizationDetailComponent;
  let fixture: ComponentFixture<SetEntityByOrganizationDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SetEntityByOrganizationDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./set-entity-by-organization-detail.component').then(m => m.SetEntityByOrganizationDetailComponent),
              resolve: { setEntityByOrganization: () => of({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SetEntityByOrganizationDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SetEntityByOrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load setEntityByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SetEntityByOrganizationDetailComponent);

      // THEN
      expect(instance.setEntityByOrganization()).toEqual(
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
