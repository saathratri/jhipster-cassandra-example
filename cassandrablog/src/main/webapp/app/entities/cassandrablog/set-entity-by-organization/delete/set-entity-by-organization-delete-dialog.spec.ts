import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { of } from 'rxjs';

import { SetEntityByOrganizationService } from '../service/set-entity-by-organization.service';
import { sampleWithRequiredData } from '../set-entity-by-organization.test-samples';

import { SetEntityByOrganizationDeleteDialogComponent } from './set-entity-by-organization-delete-dialog';

describe('SetEntityByOrganization Management Delete Component', () => {
  let comp: SetEntityByOrganizationDeleteDialogComponent;
  let fixture: ComponentFixture<SetEntityByOrganizationDeleteDialogComponent>;
  let service: SetEntityByOrganizationService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NgbActiveModal],
    });
    fixture = TestBed.createComponent(SetEntityByOrganizationDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SetEntityByOrganizationService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('should call delete service on confirmDelete', () => {
      // GIVEN
      vitest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse<{}>()));
      vitest.spyOn(mockActiveModal, 'close');

      // WHEN
      comp.confirmDelete(sampleWithRequiredData.organizationId);

      // THEN
      expect(service.delete).toHaveBeenCalledWith(sampleWithRequiredData.organizationId);
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
