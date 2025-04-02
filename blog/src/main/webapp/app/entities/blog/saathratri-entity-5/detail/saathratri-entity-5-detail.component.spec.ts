import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SaathratriEntity5DetailComponent } from './saathratri-entity-5-detail.component';

describe('SaathratriEntity5 Management Detail Component', () => {
  let comp: SaathratriEntity5DetailComponent;
  let fixture: ComponentFixture<SaathratriEntity5DetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaathratriEntity5DetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./saathratri-entity-5-detail.component').then(m => m.SaathratriEntity5DetailComponent),
              resolve: { saathratriEntity5: () => of({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SaathratriEntity5DetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaathratriEntity5DetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load saathratriEntity5 on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SaathratriEntity5DetailComponent);

      // THEN
      expect(instance.saathratriEntity5()).toEqual(expect.objectContaining({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' }));
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
