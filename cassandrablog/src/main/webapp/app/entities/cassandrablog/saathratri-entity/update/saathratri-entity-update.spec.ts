import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ISaathratriEntity } from '../saathratri-entity.model';
import { sampleWithRequiredData } from '../saathratri-entity.test-samples';
import { SaathratriEntityService } from '../service/saathratri-entity.service';

import { SaathratriEntityFormService } from './saathratri-entity-form.service';
import { SaathratriEntityUpdateComponent } from './saathratri-entity-update';

describe('SaathratriEntity Management Update Component', () => {
  let comp: SaathratriEntityUpdateComponent;
  let fixture: ComponentFixture<SaathratriEntityUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let saathratriEntityFormService: SaathratriEntityFormService;
  let saathratriEntityService: SaathratriEntityService;

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

    fixture = TestBed.createComponent(SaathratriEntityUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saathratriEntityFormService = TestBed.inject(SaathratriEntityFormService);
    saathratriEntityService = TestBed.inject(SaathratriEntityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const saathratriEntity: ISaathratriEntity = { ...sampleWithRequiredData };

      activatedRoute.data = of({ saathratriEntity });
      comp.ngOnInit();

      expect(comp.saathratriEntity).toEqual(saathratriEntity);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity>>();
      const saathratriEntity = { ...sampleWithRequiredData };
      vitest.spyOn(saathratriEntityFormService, 'getSaathratriEntity').mockReturnValue(saathratriEntity);
      vitest.spyOn(saathratriEntityService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ saathratriEntity });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saathratriEntity }));
      saveSubject.complete();

      // THEN
      expect(saathratriEntityFormService.getSaathratriEntity).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(saathratriEntityService.update).toHaveBeenCalledWith(expect.objectContaining(saathratriEntity));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity>>();
      const saathratriEntity = { ...sampleWithRequiredData };
      vitest.spyOn(saathratriEntityFormService, 'getSaathratriEntity').mockReturnValue(saathratriEntity);
      vitest.spyOn(saathratriEntityService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ saathratriEntity: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saathratriEntity }));
      saveSubject.complete();

      // THEN
      expect(saathratriEntityFormService.getSaathratriEntity).toHaveBeenCalled();
      expect(saathratriEntityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity>>();
      const saathratriEntity = { ...sampleWithRequiredData };
      vitest.spyOn(saathratriEntityService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ saathratriEntity });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saathratriEntityService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
