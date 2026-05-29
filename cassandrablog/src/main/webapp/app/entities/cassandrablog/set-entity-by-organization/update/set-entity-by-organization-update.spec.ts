import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { SetEntityByOrganizationService } from '../service/set-entity-by-organization.service';
import { ISetEntityByOrganization } from '../set-entity-by-organization.model';
import { sampleWithRequiredData } from '../set-entity-by-organization.test-samples';

import { SetEntityByOrganizationFormService } from './set-entity-by-organization-form.service';
import { SetEntityByOrganizationUpdateComponent } from './set-entity-by-organization-update';

describe('SetEntityByOrganization Management Update Component', () => {
  let comp: SetEntityByOrganizationUpdateComponent;
  let fixture: ComponentFixture<SetEntityByOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let setEntityByOrganizationFormService: SetEntityByOrganizationFormService;
  let setEntityByOrganizationService: SetEntityByOrganizationService;

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

    fixture = TestBed.createComponent(SetEntityByOrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    setEntityByOrganizationFormService = TestBed.inject(SetEntityByOrganizationFormService);
    setEntityByOrganizationService = TestBed.inject(SetEntityByOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const setEntityByOrganization: ISetEntityByOrganization = { ...sampleWithRequiredData };

      activatedRoute.data = of({ setEntityByOrganization });
      comp.ngOnInit();

      expect(comp.setEntityByOrganization).toEqual(setEntityByOrganization);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISetEntityByOrganization>>();
      const setEntityByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(setEntityByOrganizationFormService, 'getSetEntityByOrganization').mockReturnValue(setEntityByOrganization);
      vitest.spyOn(setEntityByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ setEntityByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: setEntityByOrganization }));
      saveSubject.complete();

      // THEN
      expect(setEntityByOrganizationFormService.getSetEntityByOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(setEntityByOrganizationService.update).toHaveBeenCalledWith(expect.objectContaining(setEntityByOrganization));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISetEntityByOrganization>>();
      const setEntityByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(setEntityByOrganizationFormService, 'getSetEntityByOrganization').mockReturnValue(setEntityByOrganization);
      vitest.spyOn(setEntityByOrganizationService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ setEntityByOrganization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: setEntityByOrganization }));
      saveSubject.complete();

      // THEN
      expect(setEntityByOrganizationFormService.getSetEntityByOrganization).toHaveBeenCalled();
      expect(setEntityByOrganizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISetEntityByOrganization>>();
      const setEntityByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(setEntityByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ setEntityByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(setEntityByOrganizationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
