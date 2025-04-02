import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SaathratriEntity5Service } from '../service/saathratri-entity-5.service';
import { ISaathratriEntity5 } from '../saathratri-entity-5.model';
import { SaathratriEntity5FormService } from './saathratri-entity-5-form.service';

import { SaathratriEntity5UpdateComponent } from './saathratri-entity-5-update.component';

describe('SaathratriEntity5 Management Update Component', () => {
  let comp: SaathratriEntity5UpdateComponent;
  let fixture: ComponentFixture<SaathratriEntity5UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let saathratriEntity5FormService: SaathratriEntity5FormService;
  let saathratriEntity5Service: SaathratriEntity5Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SaathratriEntity5UpdateComponent],
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
      .overrideTemplate(SaathratriEntity5UpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SaathratriEntity5UpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saathratriEntity5FormService = TestBed.inject(SaathratriEntity5FormService);
    saathratriEntity5Service = TestBed.inject(SaathratriEntity5Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const saathratriEntity5: ISaathratriEntity5 = { organizationId: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ saathratriEntity5 });
      comp.ngOnInit();

      expect(comp.saathratriEntity5).toEqual(saathratriEntity5);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity5>>();
      const saathratriEntity5 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(saathratriEntity5FormService, 'getSaathratriEntity5').mockReturnValue(saathratriEntity5);
      jest.spyOn(saathratriEntity5Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity5 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saathratriEntity5 }));
      saveSubject.complete();

      // THEN
      expect(saathratriEntity5FormService.getSaathratriEntity5).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(saathratriEntity5Service.update).toHaveBeenCalledWith(expect.objectContaining(saathratriEntity5));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity5>>();
      const saathratriEntity5 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(saathratriEntity5FormService, 'getSaathratriEntity5').mockReturnValue({ organizationId: null });
      jest.spyOn(saathratriEntity5Service, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity5: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saathratriEntity5 }));
      saveSubject.complete();

      // THEN
      expect(saathratriEntity5FormService.getSaathratriEntity5).toHaveBeenCalled();
      expect(saathratriEntity5Service.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity5>>();
      const saathratriEntity5 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(saathratriEntity5Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity5 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saathratriEntity5Service.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
