import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ISaathratriEntity3 } from '../saathratri-entity-3.model';
import { SaathratriEntity3Service } from '../service/saathratri-entity-3.service';

import { SaathratriEntity3FormService } from './saathratri-entity-3-form.service';
import { SaathratriEntity3Update } from './saathratri-entity-3-update';

describe('SaathratriEntity3 Management Update Component', () => {
  let comp: SaathratriEntity3Update;
  let fixture: ComponentFixture<SaathratriEntity3Update>;
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
          },
        },
      ],
    });

    fixture = TestBed.createComponent(SaathratriEntity3Update);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saathratriEntity3FormService = TestBed.inject(SaathratriEntity3FormService);
    saathratriEntity3Service = TestBed.inject(SaathratriEntity3Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const saathratriEntity3: ISaathratriEntity3 = { entityType: 'a8b4fb97-a92e-4ff7-90af-c8ae72476ab0' };

      activatedRoute.data = of({ saathratriEntity3 });
      comp.ngOnInit();

      expect(comp.saathratriEntity3).toEqual(saathratriEntity3);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<ISaathratriEntity3>();
      const saathratriEntity3 = { entityType: 'b211e691-84ee-487a-888a-c6d141e895e8' };
      vitest.spyOn(saathratriEntity3FormService, 'getSaathratriEntity3').mockReturnValue(saathratriEntity3);
      vitest.spyOn(saathratriEntity3Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity3 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(saathratriEntity3);
      saveSubject.complete();

      // THEN
      expect(saathratriEntity3FormService.getSaathratriEntity3).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(saathratriEntity3Service.update).toHaveBeenCalledWith(expect.objectContaining(saathratriEntity3));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<ISaathratriEntity3>();
      const saathratriEntity3 = { entityType: 'b211e691-84ee-487a-888a-c6d141e895e8' };
      vitest.spyOn(saathratriEntity3FormService, 'getSaathratriEntity3').mockReturnValue({ entityType: null });
      vitest.spyOn(saathratriEntity3Service, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity3: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(saathratriEntity3);
      saveSubject.complete();

      // THEN
      expect(saathratriEntity3FormService.getSaathratriEntity3).toHaveBeenCalled();
      expect(saathratriEntity3Service.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<ISaathratriEntity3>();
      const saathratriEntity3 = { entityType: 'b211e691-84ee-487a-888a-c6d141e895e8' };
      vitest.spyOn(saathratriEntity3Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity3 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saathratriEntity3Service.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
