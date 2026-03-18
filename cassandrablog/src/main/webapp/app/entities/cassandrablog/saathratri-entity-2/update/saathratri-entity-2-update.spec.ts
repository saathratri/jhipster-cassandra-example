import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ISaathratriEntity2 } from '../saathratri-entity-2.model';
import { SaathratriEntity2Service } from '../service/saathratri-entity-2.service';

import { SaathratriEntity2FormService } from './saathratri-entity-2-form.service';
import { SaathratriEntity2Update } from './saathratri-entity-2-update';

describe('SaathratriEntity2 Management Update Component', () => {
  let comp: SaathratriEntity2Update;
  let fixture: ComponentFixture<SaathratriEntity2Update>;
  let activatedRoute: ActivatedRoute;
  let saathratriEntity2FormService: SaathratriEntity2FormService;
  let saathratriEntity2Service: SaathratriEntity2Service;

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

    fixture = TestBed.createComponent(SaathratriEntity2Update);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saathratriEntity2FormService = TestBed.inject(SaathratriEntity2FormService);
    saathratriEntity2Service = TestBed.inject(SaathratriEntity2Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const saathratriEntity2: ISaathratriEntity2 = { entityTypeId: '6d1fa154-3bf3-4062-a599-7451256404c9' };

      activatedRoute.data = of({ saathratriEntity2 });
      comp.ngOnInit();

      expect(comp.saathratriEntity2).toEqual(saathratriEntity2);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<ISaathratriEntity2>();
      const saathratriEntity2 = { entityTypeId: '478effd9-630b-455d-b46d-efa51cd24482' };
      vitest.spyOn(saathratriEntity2FormService, 'getSaathratriEntity2').mockReturnValue(saathratriEntity2);
      vitest.spyOn(saathratriEntity2Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity2 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(saathratriEntity2);
      saveSubject.complete();

      // THEN
      expect(saathratriEntity2FormService.getSaathratriEntity2).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(saathratriEntity2Service.update).toHaveBeenCalledWith(expect.objectContaining(saathratriEntity2));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<ISaathratriEntity2>();
      const saathratriEntity2 = { entityTypeId: '478effd9-630b-455d-b46d-efa51cd24482' };
      vitest.spyOn(saathratriEntity2FormService, 'getSaathratriEntity2').mockReturnValue({ entityTypeId: null });
      vitest.spyOn(saathratriEntity2Service, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity2: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(saathratriEntity2);
      saveSubject.complete();

      // THEN
      expect(saathratriEntity2FormService.getSaathratriEntity2).toHaveBeenCalled();
      expect(saathratriEntity2Service.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<ISaathratriEntity2>();
      const saathratriEntity2 = { entityTypeId: '478effd9-630b-455d-b46d-efa51cd24482' };
      vitest.spyOn(saathratriEntity2Service, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity2 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saathratriEntity2Service.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
