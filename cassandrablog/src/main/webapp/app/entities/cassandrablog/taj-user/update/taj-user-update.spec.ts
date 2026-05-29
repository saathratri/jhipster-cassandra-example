import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { TajUserService } from '../service/taj-user.service';
import { ITajUser } from '../taj-user.model';
import { sampleWithRequiredData } from '../taj-user.test-samples';

import { TajUserFormService } from './taj-user-form.service';
import { TajUserUpdateComponent } from './taj-user-update';

describe('TajUser Management Update Component', () => {
  let comp: TajUserUpdateComponent;
  let fixture: ComponentFixture<TajUserUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tajUserFormService: TajUserFormService;
  let tajUserService: TajUserService;

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

    fixture = TestBed.createComponent(TajUserUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tajUserFormService = TestBed.inject(TajUserFormService);
    tajUserService = TestBed.inject(TajUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const tajUser: ITajUser = { ...sampleWithRequiredData };

      activatedRoute.data = of({ tajUser });
      comp.ngOnInit();

      expect(comp.tajUser).toEqual(tajUser);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITajUser>>();
      const tajUser = { ...sampleWithRequiredData };
      vitest.spyOn(tajUserFormService, 'getTajUser').mockReturnValue(tajUser);
      vitest.spyOn(tajUserService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tajUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tajUser }));
      saveSubject.complete();

      // THEN
      expect(tajUserFormService.getTajUser).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tajUserService.update).toHaveBeenCalledWith(expect.objectContaining(tajUser));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITajUser>>();
      const tajUser = { ...sampleWithRequiredData };
      vitest.spyOn(tajUserFormService, 'getTajUser').mockReturnValue(tajUser);
      vitest.spyOn(tajUserService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ tajUser: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tajUser }));
      saveSubject.complete();

      // THEN
      expect(tajUserFormService.getTajUser).toHaveBeenCalled();
      expect(tajUserService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITajUser>>();
      const tajUser = { ...sampleWithRequiredData };
      vitest.spyOn(tajUserService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tajUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tajUserService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
