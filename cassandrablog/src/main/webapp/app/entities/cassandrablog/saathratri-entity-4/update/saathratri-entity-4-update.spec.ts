import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ISaathratriEntity4 } from '../saathratri-entity-4.model';
import { sampleWithRequiredData } from '../saathratri-entity-4.test-samples';
import { SaathratriEntity4Service } from '../service/saathratri-entity-4.service';

import { SaathratriEntity4FormService } from './saathratri-entity-4-form.service';
import { SaathratriEntity4UpdateComponent } from './saathratri-entity-4-update';

describe('SaathratriEntity4 Management Update Component', () => {
  let comp: SaathratriEntity4UpdateComponent;
  let fixture: ComponentFixture<SaathratriEntity4UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let saathratriEntity4FormService: SaathratriEntity4FormService;
  let saathratriEntity4Service: SaathratriEntity4Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideHttpClientTesting(),
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
            // ngOnInit reads snapshot.routeConfig?.path to decide isNew
            snapshot: { routeConfig: { path: '' } },
          },
        },
      ],
    });

    fixture = TestBed.createComponent(SaathratriEntity4UpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saathratriEntity4FormService = TestBed.inject(SaathratriEntity4FormService);
    saathratriEntity4Service = TestBed.inject(SaathratriEntity4Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const saathratriEntity4: ISaathratriEntity4 = { ...sampleWithRequiredData };

      activatedRoute.data = of({ saathratriEntity4 });
      comp.ngOnInit();

      expect(comp.saathratriEntity4).toEqual(saathratriEntity4);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity4>>();
      const saathratriEntity4 = { ...sampleWithRequiredData };
      vitest.spyOn(saathratriEntity4FormService, 'getSaathratriEntity4').mockReturnValue(saathratriEntity4);
      vitest.spyOn(saathratriEntity4Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity4 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saathratriEntity4 }));
      saveSubject.complete();

      // THEN
      expect(saathratriEntity4FormService.getSaathratriEntity4).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(saathratriEntity4Service.update).toHaveBeenCalledWith(expect.objectContaining(saathratriEntity4));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity4>>();
      const saathratriEntity4 = { ...sampleWithRequiredData };
      vitest.spyOn(saathratriEntity4FormService, 'getSaathratriEntity4').mockReturnValue(saathratriEntity4);
      vitest.spyOn(saathratriEntity4Service, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ saathratriEntity4: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saathratriEntity4 }));
      saveSubject.complete();

      // THEN
      expect(saathratriEntity4FormService.getSaathratriEntity4).toHaveBeenCalled();
      expect(saathratriEntity4Service.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity4>>();
      const saathratriEntity4 = { ...sampleWithRequiredData };
      vitest.spyOn(saathratriEntity4Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity4 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saathratriEntity4Service.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
