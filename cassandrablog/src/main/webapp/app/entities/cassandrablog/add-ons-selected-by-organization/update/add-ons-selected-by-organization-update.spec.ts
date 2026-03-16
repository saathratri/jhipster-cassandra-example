import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';

import { TranslateModule } from '@ngx-translate/core';
import { Subject, from, of } from 'rxjs';

import { IAddOnsSelectedByOrganization } from '../add-ons-selected-by-organization.model';
import { AddOnsSelectedByOrganizationService } from '../service/add-ons-selected-by-organization.service';

import { AddOnsSelectedByOrganizationFormService } from './add-ons-selected-by-organization-form.service';
import { AddOnsSelectedByOrganizationUpdate } from './add-ons-selected-by-organization-update';

describe('AddOnsSelectedByOrganization Management Update Component', () => {
  let comp: AddOnsSelectedByOrganizationUpdate;
  let fixture: ComponentFixture<AddOnsSelectedByOrganizationUpdate>;
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
          },
        },
      ],
    });

    fixture = TestBed.createComponent(AddOnsSelectedByOrganizationUpdate);
    activatedRoute = TestBed.inject(ActivatedRoute);
    addOnsSelectedByOrganizationFormService = TestBed.inject(AddOnsSelectedByOrganizationFormService);
    addOnsSelectedByOrganizationService = TestBed.inject(AddOnsSelectedByOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const addOnsSelectedByOrganization: IAddOnsSelectedByOrganization = { organizationId: '327f5417-09c8-4f5c-b313-117201e1d0b5' };

      activatedRoute.data = of({ addOnsSelectedByOrganization });
      comp.ngOnInit();

      expect(comp.addOnsSelectedByOrganization).toEqual(addOnsSelectedByOrganization);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<IAddOnsSelectedByOrganization>();
      const addOnsSelectedByOrganization = { organizationId: '5d2569e7-278f-4572-8beb-b3497c54caf8' };
      vitest
        .spyOn(addOnsSelectedByOrganizationFormService, 'getAddOnsSelectedByOrganization')
        .mockReturnValue(addOnsSelectedByOrganization);
      vitest.spyOn(addOnsSelectedByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addOnsSelectedByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(addOnsSelectedByOrganization);
      saveSubject.complete();

      // THEN
      expect(addOnsSelectedByOrganizationFormService.getAddOnsSelectedByOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(addOnsSelectedByOrganizationService.update).toHaveBeenCalledWith(expect.objectContaining(addOnsSelectedByOrganization));
      expect(comp.isSaving()).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<IAddOnsSelectedByOrganization>();
      const addOnsSelectedByOrganization = { organizationId: '5d2569e7-278f-4572-8beb-b3497c54caf8' };
      vitest.spyOn(addOnsSelectedByOrganizationFormService, 'getAddOnsSelectedByOrganization').mockReturnValue({ organizationId: null });
      vitest.spyOn(addOnsSelectedByOrganizationService, 'create').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addOnsSelectedByOrganization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.next(addOnsSelectedByOrganization);
      saveSubject.complete();

      // THEN
      expect(addOnsSelectedByOrganizationFormService.getAddOnsSelectedByOrganization).toHaveBeenCalled();
      expect(addOnsSelectedByOrganizationService.create).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<IAddOnsSelectedByOrganization>();
      const addOnsSelectedByOrganization = { organizationId: '5d2569e7-278f-4572-8beb-b3497c54caf8' };
      vitest.spyOn(addOnsSelectedByOrganizationService, 'update').mockReturnValue(saveSubject);
      vitest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addOnsSelectedByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving()).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(addOnsSelectedByOrganizationService.update).toHaveBeenCalled();
      expect(comp.isSaving()).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
