import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AddOnsAvailableByOrganizationService } from '../service/add-ons-available-by-organization.service';
import { IAddOnsAvailableByOrganization } from '../add-ons-available-by-organization.model';
import { AddOnsAvailableByOrganizationFormService } from './add-ons-available-by-organization-form.service';

import { AddOnsAvailableByOrganizationUpdateComponent } from './add-ons-available-by-organization-update.component';

describe('AddOnsAvailableByOrganization Management Update Component', () => {
  let comp: AddOnsAvailableByOrganizationUpdateComponent;
  let fixture: ComponentFixture<AddOnsAvailableByOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let addOnsAvailableByOrganizationFormService: AddOnsAvailableByOrganizationFormService;
  let addOnsAvailableByOrganizationService: AddOnsAvailableByOrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AddOnsAvailableByOrganizationUpdateComponent],
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
      .overrideTemplate(AddOnsAvailableByOrganizationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AddOnsAvailableByOrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    addOnsAvailableByOrganizationFormService = TestBed.inject(AddOnsAvailableByOrganizationFormService);
    addOnsAvailableByOrganizationService = TestBed.inject(AddOnsAvailableByOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const addOnsAvailableByOrganization: IAddOnsAvailableByOrganization = { organizationId: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ addOnsAvailableByOrganization });
      comp.ngOnInit();

      expect(comp.addOnsAvailableByOrganization).toEqual(addOnsAvailableByOrganization);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddOnsAvailableByOrganization>>();
      const addOnsAvailableByOrganization = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest
        .spyOn(addOnsAvailableByOrganizationFormService, 'getAddOnsAvailableByOrganization')
        .mockReturnValue(addOnsAvailableByOrganization);
      jest.spyOn(addOnsAvailableByOrganizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addOnsAvailableByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: addOnsAvailableByOrganization }));
      saveSubject.complete();

      // THEN
      expect(addOnsAvailableByOrganizationFormService.getAddOnsAvailableByOrganization).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(addOnsAvailableByOrganizationService.update).toHaveBeenCalledWith(expect.objectContaining(addOnsAvailableByOrganization));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddOnsAvailableByOrganization>>();
      const addOnsAvailableByOrganization = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(addOnsAvailableByOrganizationFormService, 'getAddOnsAvailableByOrganization').mockReturnValue({ organizationId: null });
      jest.spyOn(addOnsAvailableByOrganizationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addOnsAvailableByOrganization: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: addOnsAvailableByOrganization }));
      saveSubject.complete();

      // THEN
      expect(addOnsAvailableByOrganizationFormService.getAddOnsAvailableByOrganization).toHaveBeenCalled();
      expect(addOnsAvailableByOrganizationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddOnsAvailableByOrganization>>();
      const addOnsAvailableByOrganization = { organizationId: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(addOnsAvailableByOrganizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ addOnsAvailableByOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(addOnsAvailableByOrganizationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
