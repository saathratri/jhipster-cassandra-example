jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AddOnsAvailableByOrganizationService } from '../service/add-ons-available-by-organization.service';

import { AddOnsAvailableByOrganizationDeleteDialogComponent } from './add-ons-available-by-organization-delete-dialog.component';

describe('AddOnsAvailableByOrganization Management Delete Component', () => {
  let comp: AddOnsAvailableByOrganizationDeleteDialogComponent;
  let fixture: ComponentFixture<AddOnsAvailableByOrganizationDeleteDialogComponent>;
  let service: AddOnsAvailableByOrganizationService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AddOnsAvailableByOrganizationDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AddOnsAvailableByOrganizationDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AddOnsAvailableByOrganizationDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AddOnsAvailableByOrganizationService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete('9fec3727-3421-4967-b213-ba36557ca194');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
