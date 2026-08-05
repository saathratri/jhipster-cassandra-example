import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpResponse } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { of } from 'rxjs';

import { sampleWithRequiredData } from '../landing-page-by-organization.test-samples';
import { LandingPageByOrganizationService } from '../service/landing-page-by-organization.service';

import { LandingPageByOrganizationDeleteDialogComponent } from './landing-page-by-organization-delete-dialog';

describe('LandingPageByOrganization Management Delete Component', () => {
  let comp: LandingPageByOrganizationDeleteDialogComponent;
  let fixture: ComponentFixture<LandingPageByOrganizationDeleteDialogComponent>;
  let service: LandingPageByOrganizationService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NgbActiveModal],
    });
    fixture = TestBed.createComponent(LandingPageByOrganizationDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LandingPageByOrganizationService);
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
