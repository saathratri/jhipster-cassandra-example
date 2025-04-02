import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SaathratriEntity6Service } from '../service/saathratri-entity-6.service';
import { ISaathratriEntity6 } from '../saathratri-entity-6.model';
import { SaathratriEntity6FormService } from './saathratri-entity-6-form.service';

import { SaathratriEntity6UpdateComponent } from './saathratri-entity-6-update.component';

describe('SaathratriEntity6 Management Update Component', () => {
  let comp: SaathratriEntity6UpdateComponent;
  let fixture: ComponentFixture<SaathratriEntity6UpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let saathratriEntity6FormService: SaathratriEntity6FormService;
  let saathratriEntity6Service: SaathratriEntity6Service;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SaathratriEntity6UpdateComponent],
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
      .overrideTemplate(SaathratriEntity6UpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SaathratriEntity6UpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saathratriEntity6FormService = TestBed.inject(SaathratriEntity6FormService);
    saathratriEntity6Service = TestBed.inject(SaathratriEntity6Service);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const saathratriEntity6: ISaathratriEntity6 = { organizationId: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ saathratriEntity6 });
      comp.ngOnInit();

      expect(comp.saathratriEntity6).toEqual(saathratriEntity6);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity6>>();
      const saathratriEntity6 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(saathratriEntity6FormService, 'getSaathratriEntity6').mockReturnValue(saathratriEntity6);
      jest.spyOn(saathratriEntity6Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity6 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saathratriEntity6 }));
      saveSubject.complete();

      // THEN
      expect(saathratriEntity6FormService.getSaathratriEntity6).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(saathratriEntity6Service.update).toHaveBeenCalledWith(expect.objectContaining(saathratriEntity6));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity6>>();
      const saathratriEntity6 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(saathratriEntity6FormService, 'getSaathratriEntity6').mockReturnValue({ organizationId: null });
      jest.spyOn(saathratriEntity6Service, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity6: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: saathratriEntity6 }));
      saveSubject.complete();

      // THEN
      expect(saathratriEntity6FormService.getSaathratriEntity6).toHaveBeenCalled();
      expect(saathratriEntity6Service.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISaathratriEntity6>>();
      const saathratriEntity6 = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(saathratriEntity6Service, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ saathratriEntity6 });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saathratriEntity6Service.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
