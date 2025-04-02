import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SaathratriEntity3DetailComponent } from './saathratri-entity-3-detail.component';

describe('SaathratriEntity3 Management Detail Component', () => {
  let comp: SaathratriEntity3DetailComponent;
  let fixture: ComponentFixture<SaathratriEntity3DetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaathratriEntity3DetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./saathratri-entity-3-detail.component').then(m => m.SaathratriEntity3DetailComponent),
              resolve: { saathratriEntity3: () => of({ entityType: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SaathratriEntity3DetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaathratriEntity3DetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load saathratriEntity3 on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SaathratriEntity3DetailComponent);

      // THEN
      expect(instance.saathratriEntity3()).toEqual(expect.objectContaining({ entityType: 'ABC' }));
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
