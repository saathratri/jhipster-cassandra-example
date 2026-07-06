import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ISaathratriEntity3 } from '../saathratri-entity-3.model';
import { sampleWithRequiredData } from '../saathratri-entity-3.test-samples';
import { SaathratriEntity3Service } from '../service/saathratri-entity-3.service';

import { SaathratriEntity3FormService } from './saathratri-entity-3-form.service';
import { SaathratriEntity3UpdateComponent } from './saathratri-entity-3-update';

describe('SaathratriEntity3 Management Update Component', () => {
  let comp: SaathratriEntity3UpdateComponent;
  let fixture: ComponentFixture<SaathratriEntity3UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let saathratriEntity3FormService: SaathratriEntity3FormService;
  let saathratriEntity3Service: SaathratriEntity3Service;

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

    fixture = TestBed.createComponent(SaathratriEntity3UpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saathratriEntity3FormService = TestBed.inject(SaathratriEntity3FormService);
    saathratriEntity3Service = TestBed.inject(SaathratriEntity3Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const saathratriEntity3: ISaathratriEntity3 = { ...sampleWithRequiredData };

      activatedRoute.data = of({ saathratriEntity3 });
      comp.ngOnInit();

      expect(comp.saathratriEntity3).toEqual(saathratriEntity3);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity3>>();
      const saathratriEntity3 = { ...sampleWithRequiredData };
      vitest.spyOn(saathratriEntity3FormService, 'getSaathratriEntity3').mockReturnValue(saathratriEntity3);
      vitest.spyOn(saathratriEntity3Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ saathratriEntity3 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saathratriEntity3 }));
      saveSubject.complete();

      // THEN
      expect(saathratriEntity3FormService.getSaathratriEntity3).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(saathratriEntity3Service.update).toHaveBeenCalledWith(expect.objectContaining(saathratriEntity3));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity3>>();
      const saathratriEntity3 = { ...sampleWithRequiredData };
      vitest.spyOn(saathratriEntity3FormService, 'getSaathratriEntity3').mockReturnValue(saathratriEntity3);
      vitest.spyOn(saathratriEntity3Service, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ saathratriEntity3: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saathratriEntity3 }));
      saveSubject.complete();

      // THEN
      expect(saathratriEntity3FormService.getSaathratriEntity3).toHaveBeenCalled();
      expect(saathratriEntity3Service.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity3>>();
      const saathratriEntity3 = { ...sampleWithRequiredData };
      vitest.spyOn(saathratriEntity3Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ saathratriEntity3 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saathratriEntity3Service.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
