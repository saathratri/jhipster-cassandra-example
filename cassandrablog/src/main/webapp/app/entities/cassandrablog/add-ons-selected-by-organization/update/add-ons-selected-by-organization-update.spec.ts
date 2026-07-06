import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IAddOnsSelectedByOrganization } from '../add-ons-selected-by-organization.model';
import { sampleWithRequiredData } from '../add-ons-selected-by-organization.test-samples';
import { AddOnsSelectedByOrganizationService } from '../service/add-ons-selected-by-organization.service';

import { AddOnsSelectedByOrganizationFormService } from './add-ons-selected-by-organization-form.service';
import { AddOnsSelectedByOrganizationUpdateComponent } from './add-ons-selected-by-organization-update';

describe('AddOnsSelectedByOrganization Management Update Component', () => {
  let comp: AddOnsSelectedByOrganizationUpdateComponent;
  let fixture: ComponentFixture<AddOnsSelectedByOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let addOnsSelectedByOrganizationFormService: AddOnsSelectedByOrganizationFormService;
  let addOnsSelectedByOrganizationService: AddOnsSelectedByOrganizationService;

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

    fixture = TestBed.createComponent(AddOnsSelectedByOrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    addOnsSelectedByOrganizationFormService = TestBed.inject(AddOnsSelectedByOrganizationFormService);
    addOnsSelectedByOrganizationService = TestBed.inject(AddOnsSelectedByOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const addOnsSelectedByOrganization: IAddOnsSelectedByOrganization = { ...sampleWithRequiredData };

      activatedRoute.data = of({ addOnsSelectedByOrganization });
      comp.ngOnInit();

      expect(comp.addOnsSelectedByOrganization).toEqual(addOnsSelectedByOrganization);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddOnsSelectedByOrganization>>();
      const addOnsSelectedByOrganization = { ...sampleWithRequiredData };
      vitest
        .spyOn(addOnsSelectedByOrganizationFormService, 'getAddOnsSelectedByOrganization')
        .mockReturnValue(addOnsSelectedByOrganization);
      vitest.spyOn(addOnsSelectedByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ addOnsSelectedByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: addOnsSelectedByOrganization }));
      saveSubject.complete();

      // THEN
      expect(addOnsSelectedByOrganizationFormService.getAddOnsSelectedByOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(addOnsSelectedByOrganizationService.update).toHaveBeenCalledWith(expect.objectContaining(addOnsSelectedByOrganization));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddOnsSelectedByOrganization>>();
      const addOnsSelectedByOrganization = { ...sampleWithRequiredData };
      vitest
        .spyOn(addOnsSelectedByOrganizationFormService, 'getAddOnsSelectedByOrganization')
        .mockReturnValue(addOnsSelectedByOrganization);
      vitest.spyOn(addOnsSelectedByOrganizationService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ addOnsSelectedByOrganization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: addOnsSelectedByOrganization }));
      saveSubject.complete();

      // THEN
      expect(addOnsSelectedByOrganizationFormService.getAddOnsSelectedByOrganization).toHaveBeenCalled();
      expect(addOnsSelectedByOrganizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddOnsSelectedByOrganization>>();
      const addOnsSelectedByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(addOnsSelectedByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ addOnsSelectedByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(addOnsSelectedByOrganizationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
