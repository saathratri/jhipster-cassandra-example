jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SaathratriEntity3Service } from '../service/saathratri-entity-3.service';

import { SaathratriEntity3DeleteDialogComponent } from './saathratri-entity-3-delete-dialog.component';

describe('SaathratriEntity3 Management Delete Component', () => {
  let comp: SaathratriEntity3DeleteDialogComponent;
  let fixture: ComponentFixture<SaathratriEntity3DeleteDialogComponent>;
  let service: SaathratriEntity3Service;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SaathratriEntity3DeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(SaathratriEntity3DeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SaathratriEntity3DeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SaathratriEntity3Service);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete('ABC');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('ABC');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
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
