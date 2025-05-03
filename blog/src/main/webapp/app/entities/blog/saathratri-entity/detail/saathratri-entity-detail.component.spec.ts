import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SaathratriEntityDetailComponent } from './saathratri-entity-detail.component';

describe('SaathratriEntity Management Detail Component', () => {
  let comp: SaathratriEntityDetailComponent;
  let fixture: ComponentFixture<SaathratriEntityDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaathratriEntityDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./saathratri-entity-detail.component').then(m => m.SaathratriEntityDetailComponent),
              resolve: { saathratriEntity: () => of({ entityId: '9a32a2a5-a4ff-46ed-920e-fb61b74090d3' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SaathratriEntityDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaathratriEntityDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load saathratriEntity on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SaathratriEntityDetailComponent);

      // THEN
      expect(instance.saathratriEntity()).toEqual(expect.objectContaining({ entityId: '9a32a2a5-a4ff-46ed-920e-fb61b74090d3' }));
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
