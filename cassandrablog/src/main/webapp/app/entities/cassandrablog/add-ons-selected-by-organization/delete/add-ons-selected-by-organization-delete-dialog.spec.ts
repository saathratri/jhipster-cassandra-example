import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { of } from 'rxjs';

import { sampleWithRequiredData } from '../add-ons-selected-by-organization.test-samples';
import { AddOnsSelectedByOrganizationService } from '../service/add-ons-selected-by-organization.service';

import { AddOnsSelectedByOrganizationDeleteDialogComponent } from './add-ons-selected-by-organization-delete-dialog';

describe('AddOnsSelectedByOrganization Management Delete Component', () => {
  let comp: AddOnsSelectedByOrganizationDeleteDialogComponent;
  let fixture: ComponentFixture<AddOnsSelectedByOrganizationDeleteDialogComponent>;
  let service: AddOnsSelectedByOrganizationService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NgbActiveModal],
    });
    fixture = TestBed.createComponent(AddOnsSelectedByOrganizationDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AddOnsSelectedByOrganizationService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('should call delete service on confirmDelete', () => {
      // GIVEN
      vitest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse<{}>()));
      vitest.spyOn(mockActiveModal, 'close');

      // WHEN
      comp.confirmDelete(sampleWithRequiredData);

      // THEN
      expect(service.delete).toHaveBeenCalledWith(sampleWithRequiredData);
      expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
    });

    it('should not call delete service on clear', () => {
      // GIVEN
      vitest.spyOn(service, 'delete');
      vitest.spyOn(mockActiveModal, 'close');
      vitest.spyOn(mockActiveModal, 'dismiss');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
