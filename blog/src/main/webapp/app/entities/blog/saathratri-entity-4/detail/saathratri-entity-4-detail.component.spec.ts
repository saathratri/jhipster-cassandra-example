import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SaathratriEntity4DetailComponent } from './saathratri-entity-4-detail.component';

describe('SaathratriEntity4 Management Detail Component', () => {
  let comp: SaathratriEntity4DetailComponent;
  let fixture: ComponentFixture<SaathratriEntity4DetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaathratriEntity4DetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./saathratri-entity-4-detail.component').then(m => m.SaathratriEntity4DetailComponent),
              resolve: { saathratriEntity4: () => of({ organizationId: 'f84082d7-31a6-42c6-a2c0-915e5221b32b' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SaathratriEntity4DetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaathratriEntity4DetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load saathratriEntity4 on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SaathratriEntity4DetailComponent);

      // THEN
      expect(instance.saathratriEntity4()).toEqual(expect.objectContaining({ organizationId: 'f84082d7-31a6-42c6-a2c0-915e5221b32b' }));
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
