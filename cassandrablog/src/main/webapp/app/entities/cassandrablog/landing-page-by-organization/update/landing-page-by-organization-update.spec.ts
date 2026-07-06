import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { ILandingPageByOrganization } from '../landing-page-by-organization.model';
import { sampleWithRequiredData } from '../landing-page-by-organization.test-samples';
import { LandingPageByOrganizationService } from '../service/landing-page-by-organization.service';

import { LandingPageByOrganizationFormService } from './landing-page-by-organization-form.service';
import { LandingPageByOrganizationUpdateComponent } from './landing-page-by-organization-update';

describe('LandingPageByOrganization Management Update Component', () => {
  let comp: LandingPageByOrganizationUpdateComponent;
  let fixture: ComponentFixture<LandingPageByOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let landingPageByOrganizationFormService: LandingPageByOrganizationFormService;
  let landingPageByOrganizationService: LandingPageByOrganizationService;

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

    fixture = TestBed.createComponent(LandingPageByOrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    landingPageByOrganizationFormService = TestBed.inject(LandingPageByOrganizationFormService);
    landingPageByOrganizationService = TestBed.inject(LandingPageByOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const landingPageByOrganization: ILandingPageByOrganization = { ...sampleWithRequiredData };

      activatedRoute.data = of({ landingPageByOrganization });
      comp.ngOnInit();

      expect(comp.landingPageByOrganization).toEqual(landingPageByOrganization);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILandingPageByOrganization>>();
      const landingPageByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(landingPageByOrganizationFormService, 'getLandingPageByOrganization').mockReturnValue(landingPageByOrganization);
      vitest.spyOn(landingPageByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ landingPageByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: landingPageByOrganization }));
      saveSubject.complete();

      // THEN
      expect(landingPageByOrganizationFormService.getLandingPageByOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(landingPageByOrganizationService.update).toHaveBeenCalledWith(expect.objectContaining(landingPageByOrganization));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILandingPageByOrganization>>();
      const landingPageByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(landingPageByOrganizationFormService, 'getLandingPageByOrganization').mockReturnValue(landingPageByOrganization);
      vitest.spyOn(landingPageByOrganizationService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      // routeConfig.path === 'new' makes the component treat this as a create
      (activatedRoute as unknown as { snapshot: unknown }).snapshot = { routeConfig: { path: 'new' } };
      activatedRoute.data = of({ landingPageByOrganization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: landingPageByOrganization }));
      saveSubject.complete();

      // THEN
      expect(landingPageByOrganizationFormService.getLandingPageByOrganization).toHaveBeenCalled();
      expect(landingPageByOrganizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILandingPageByOrganization>>();
      const landingPageByOrganization = { ...sampleWithRequiredData };
      vitest.spyOn(landingPageByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState').mockImplementation(() => {});
      activatedRoute.data = of({ landingPageByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(landingPageByOrganizationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
