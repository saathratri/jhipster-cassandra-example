import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SaathratriEntity3Service } from '../service/saathratri-entity-3.service';
import { ISaathratriEntity3 } from '../saathratri-entity-3.model';
import { SaathratriEntity3FormService } from './saathratri-entity-3-form.service';

import { SaathratriEntity3UpdateComponent } from './saathratri-entity-3-update.component';

describe('SaathratriEntity3 Management Update Component', () => {
  let comp: SaathratriEntity3UpdateComponent;
  let fixture: ComponentFixture<SaathratriEntity3UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let saathratriEntity3FormService: SaathratriEntity3FormService;
  let saathratriEntity3Service: SaathratriEntity3Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SaathratriEntity3UpdateComponent],
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
      .overrideTemplate(SaathratriEntity3UpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SaathratriEntity3UpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saathratriEntity3FormService = TestBed.inject(SaathratriEntity3FormService);
    saathratriEntity3Service = TestBed.inject(SaathratriEntity3Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const saathratriEntity3: ISaathratriEntity3 = { entityType: 'CBA' };

      activatedRoute.data = of({ saathratriEntity3 });
      comp.ngOnInit();

      expect(comp.saathratriEntity3).toEqual(saathratriEntity3);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity3>>();
      const saathratriEntity3 = { entityType: 'ABC' };
      jest.spyOn(saathratriEntity3FormService, 'getSaathratriEntity3').mockReturnValue(saathratriEntity3);
      jest.spyOn(saathratriEntity3Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
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

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity3>>();
      const saathratriEntity3 = { entityType: 'ABC' };
      jest.spyOn(saathratriEntity3FormService, 'getSaathratriEntity3').mockReturnValue({ entityType: null });
      jest.spyOn(saathratriEntity3Service, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
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

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity3>>();
      const saathratriEntity3 = { entityType: 'ABC' };
      jest.spyOn(saathratriEntity3Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
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
