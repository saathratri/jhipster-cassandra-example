import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SaathratriEntity2DetailComponent } from './saathratri-entity-2-detail.component';

describe('SaathratriEntity2 Management Detail Component', () => {
  let comp: SaathratriEntity2DetailComponent;
  let fixture: ComponentFixture<SaathratriEntity2DetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaathratriEntity2DetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./saathratri-entity-2-detail.component').then(m => m.SaathratriEntity2DetailComponent),
              resolve: { saathratriEntity2: () => of({ entityTypeId: '9fec3727-3421-4967-b213-ba36557ca194' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SaathratriEntity2DetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaathratriEntity2DetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load saathratriEntity2 on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SaathratriEntity2DetailComponent);

      // THEN
      expect(instance.saathratriEntity2()).toEqual(expect.objectContaining({ entityTypeId: '9fec3727-3421-4967-b213-ba36557ca194' }));
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
