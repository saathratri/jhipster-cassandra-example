import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SaathratriEntity2Service } from '../service/saathratri-entity-2.service';
import { ISaathratriEntity2 } from '../saathratri-entity-2.model';
import { SaathratriEntity2FormService } from './saathratri-entity-2-form.service';

import { SaathratriEntity2UpdateComponent } from './saathratri-entity-2-update.component';

describe('SaathratriEntity2 Management Update Component', () => {
  let comp: SaathratriEntity2UpdateComponent;
  let fixture: ComponentFixture<SaathratriEntity2UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let saathratriEntity2FormService: SaathratriEntity2FormService;
  let saathratriEntity2Service: SaathratriEntity2Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SaathratriEntity2UpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SaathratriEntity2UpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SaathratriEntity2UpdateComponent);
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
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity2>>();
      const saathratriEntity2 = { entityTypeId: '478effd9-630b-455d-b46d-efa51cd24482' };
      jest.spyOn(saathratriEntity2FormService, 'getSaathratriEntity2').mockReturnValue(saathratriEntity2);
      jest.spyOn(saathratriEntity2Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity2 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saathratriEntity2 }));
      saveSubject.complete();

      // THEN
      expect(saathratriEntity2FormService.getSaathratriEntity2).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(saathratriEntity2Service.update).toHaveBeenCalledWith(expect.objectContaining(saathratriEntity2));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity2>>();
      const saathratriEntity2 = { entityTypeId: '478effd9-630b-455d-b46d-efa51cd24482' };
      jest.spyOn(saathratriEntity2FormService, 'getSaathratriEntity2').mockReturnValue({ entityTypeId: null });
      jest.spyOn(saathratriEntity2Service, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity2: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saathratriEntity2 }));
      saveSubject.complete();

      // THEN
      expect(saathratriEntity2FormService.getSaathratriEntity2).toHaveBeenCalled();
      expect(saathratriEntity2Service.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity2>>();
      const saathratriEntity2 = { entityTypeId: '478effd9-630b-455d-b46d-efa51cd24482' };
      jest.spyOn(saathratriEntity2Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity2 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saathratriEntity2Service.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
