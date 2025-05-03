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
              resolve: { setEntityByOrganization: () => of({ organizationId: '40935352-8287-4b7d-a817-6dac8e5db644' }) },
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
    it('should load setEntityByOrganization on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SetEntityByOrganizationDetailComponent);

      // THEN
      expect(instance.setEntityByOrganization()).toEqual(
        expect.objectContaining({ organizationId: '40935352-8287-4b7d-a817-6dac8e5db644' }),
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
