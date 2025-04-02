import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SaathratriEntity6DetailComponent } from './saathratri-entity-6-detail.component';

describe('SaathratriEntity6 Management Detail Component', () => {
  let comp: SaathratriEntity6DetailComponent;
  let fixture: ComponentFixture<SaathratriEntity6DetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaathratriEntity6DetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./saathratri-entity-6-detail.component').then(m => m.SaathratriEntity6DetailComponent),
              resolve: { saathratriEntity6: () => of({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SaathratriEntity6DetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaathratriEntity6DetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load saathratriEntity6 on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SaathratriEntity6DetailComponent);

      // THEN
      expect(instance.saathratriEntity6()).toEqual(expect.objectContaining({ organizationId: '9fec3727-3421-4967-b213-ba36557ca194' }));
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
