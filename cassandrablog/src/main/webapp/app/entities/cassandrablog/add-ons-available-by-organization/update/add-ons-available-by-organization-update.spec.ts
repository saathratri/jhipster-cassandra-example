import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IAddOnsAvailableByOrganization } from '../add-ons-available-by-organization.model';
import { AddOnsAvailableByOrganizationService } from '../service/add-ons-available-by-organization.service';

import { AddOnsAvailableByOrganizationFormService } from './add-ons-available-by-organization-form.service';
import { AddOnsAvailableByOrganizationUpdate } from './add-ons-available-by-organization-update';

describe('AddOnsAvailableByOrganization Management Update Component', () => {
  let comp: AddOnsAvailableByOrganizationUpdate;
  let fixture: ComponentFixture<AddOnsAvailableByOrganizationUpdate>;
  let activatedRoute: ActivatedRoute;
  let addOnsAvailableByOrganizationFormService: AddOnsAvailableByOrganizationFormService;
  let addOnsAvailableByOrganizationService: AddOnsAvailableByOrganizationService;

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

    fixture = TestBed.createComponent(AddOnsAvailableByOrganizationUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    addOnsAvailableByOrganizationFormService = TestBed.inject(AddOnsAvailableByOrganizationFormService);
    addOnsAvailableByOrganizationService = TestBed.inject(AddOnsAvailableByOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const addOnsAvailableByOrganization: IAddOnsAvailableByOrganization = { organizationId: 'beb4d295-4283-4d1d-80f4-d176429ef0de' };

      activatedRoute.data = of({ addOnsAvailableByOrganization });
      comp.ngOnInit();

      expect(comp.addOnsAvailableByOrganization).toEqual(addOnsAvailableByOrganization);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IAddOnsAvailableByOrganization>();
      const addOnsAvailableByOrganization = { organizationId: 'fe29463b-6ce7-4f08-add6-0cd21f742c31' };
      vitest
        .spyOn(addOnsAvailableByOrganizationFormService, 'getAddOnsAvailableByOrganization')
        .mockReturnValue(addOnsAvailableByOrganization);
      vitest.spyOn(addOnsAvailableByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addOnsAvailableByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(addOnsAvailableByOrganization);
      saveSubject.complete();

      // THEN
      expect(addOnsAvailableByOrganizationFormService.getAddOnsAvailableByOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(addOnsAvailableByOrganizationService.update).toHaveBeenCalledWith(expect.objectContaining(addOnsAvailableByOrganization));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IAddOnsAvailableByOrganization>();
      const addOnsAvailableByOrganization = { organizationId: 'fe29463b-6ce7-4f08-add6-0cd21f742c31' };
      vitest.spyOn(addOnsAvailableByOrganizationFormService, 'getAddOnsAvailableByOrganization').mockReturnValue({ organizationId: null });
      vitest.spyOn(addOnsAvailableByOrganizationService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addOnsAvailableByOrganization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(addOnsAvailableByOrganization);
      saveSubject.complete();

      // THEN
      expect(addOnsAvailableByOrganizationFormService.getAddOnsAvailableByOrganization).toHaveBeenCalled();
      expect(addOnsAvailableByOrganizationService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IAddOnsAvailableByOrganization>();
      const addOnsAvailableByOrganization = { organizationId: 'fe29463b-6ce7-4f08-add6-0cd21f742c31' };
      vitest.spyOn(addOnsAvailableByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addOnsAvailableByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(addOnsAvailableByOrganizationService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
