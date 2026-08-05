import { beforeEach, describe, expect, it, vitest } from 'vitest';
import { HttpHeaders, HttpResponse, provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed, inject } from '@angular/core/testing';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faSort } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap/modal';
import { TranslateModule } from '@ngx-translate/core';
import { Subject, of } from 'rxjs';

import { sampleWithRequiredData } from '../landing-page-by-organization.test-samples';
import { LandingPageByOrganizationService } from '../service/landing-page-by-organization.service';

import { LandingPageByOrganizationComponent } from './landing-page-by-organization';

describe('LandingPageByOrganization Management Component', () => {
  let comp: LandingPageByOrganizationComponent;
  let fixture: ComponentFixture<LandingPageByOrganizationComponent>;
  let service: LandingPageByOrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({ defaultSort: 'id,asc' }),
            queryParamMap: of(convertToParamMap({})),
            snapshot: { queryParams: {}, queryParamMap: convertToParamMap({}) },
          },
        },
      ],
    });
    fixture = TestBed.createComponent(LandingPageByOrganizationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LandingPageByOrganizationService);
    const library = TestBed.inject(FaIconLibrary);
    library.addIcons(faSort);
  });

  it('should load all on init', () => {
    // GIVEN — the Cassandra list pages via querySlice (not query)
    vitest
      .spyOn(service, 'querySlice')
      .mockReturnValue(of(new HttpResponse({ body: [sampleWithRequiredData], headers: new HttpHeaders() })));

    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.querySlice).toHaveBeenCalled();
  });

  describe('delete', () => {
    let ngbModal: NgbModal;
    let deleteModalMock: any;

    beforeEach(() => {
      deleteModalMock = { componentInstance: {}, closed: new Subject() };
      ngbModal = (comp as any).modalService;
      vitest.spyOn(ngbModal, 'open').mockReturnValue(deleteModalMock);
    });

    it('on confirm should call load', inject([], () => {
      // GIVEN
      vitest.spyOn(comp, 'load');

      // WHEN
      comp.delete(sampleWithRequiredData);
      deleteModalMock.closed.next('deleted');

      // THEN
      expect(ngbModal.open).toHaveBeenCalled();
      expect(comp.load).toHaveBeenCalled();
    }));

    it('on dismiss should not call load', inject([], () => {
      // GIVEN
      vitest.spyOn(comp, 'load');

      // WHEN
      comp.delete(sampleWithRequiredData);
      deleteModalMock.closed.next();

      // THEN
      expect(ngbModal.open).toHaveBeenCalled();
      expect(comp.load).not.toHaveBeenCalled();
    }));
  });
});
