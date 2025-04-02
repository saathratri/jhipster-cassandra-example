import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpHeaders, HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subject, of } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { sampleWithRequiredData } from '../saathratri-entity-3.test-samples';
import { SaathratriEntity3Service } from '../service/saathratri-entity-3.service';

import { SaathratriEntity3Component } from './saathratri-entity-3.component';
import SpyInstance = jest.SpyInstance;

describe('SaathratriEntity3 Management Component', () => {
  let comp: SaathratriEntity3Component;
  let fixture: ComponentFixture<SaathratriEntity3Component>;
  let service: SaathratriEntity3Service;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SaathratriEntity3Component],
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'entityType,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'entityType,desc',
              }),
            ),
            snapshot: {
              queryParams: {},
              queryParamMap: jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'entityType,desc',
              }),
            },
          },
        },
      ],
    })
      .overrideTemplate(SaathratriEntity3Component, '')
      .compileComponents();

    fixture = TestBed.createComponent(SaathratriEntity3Component);
    comp = fixture.componentInstance;
    service = TestBed.inject(SaathratriEntity3Service);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    jest
      .spyOn(service, 'query')
      .mockReturnValueOnce(
        of(
          new HttpResponse({
            body: [{ entityType: 'ABC' }],
            headers: new HttpHeaders({
              link: '<http://localhost/api/foo?page=1&size=20>; rel="next"',
            }),
          }),
        ),
      )
      .mockReturnValueOnce(
        of(
          new HttpResponse({
            body: [{ entityType: 'CBA' }],
            headers: new HttpHeaders({
              link: '<http://localhost/api/foo?page=0&size=20>; rel="prev",<http://localhost/api/foo?page=2&size=20>; rel="next"',
            }),
          }),
        ),
      );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.saathratriEntity3s?.[0]).toEqual(expect.objectContaining({ entityType: 'ABC' }));
  });

  describe('trackEntityType', () => {
    it('Should forward to saathratriEntity3Service', () => {
      const entity = { entityType: 'ABC' };
      jest.spyOn(service, 'getSaathratriEntity3Identifier');
      const entityType = comp.trackEntityType(entity);
      expect(service.getSaathratriEntity3Identifier).toHaveBeenCalledWith(entity);
      expect(entityType).toBe(entity.entityType);
    });
  });

  it('should calculate the sort attribute for a non-id attribute', () => {
    // WHEN
    comp.navigateToWithComponentValues({ predicate: 'non-existing-column', order: 'asc' });

    // THEN
    expect(routerNavigateSpy).toHaveBeenLastCalledWith(
      expect.anything(),
      expect.objectContaining({
        queryParams: expect.objectContaining({
          sort: ['non-existing-column,asc'],
        }),
      }),
    );
  });

  it('should calculate the sort attribute for an id', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['entityType,desc'] }));
  });

  describe('delete', () => {
    let ngbModal: NgbModal;
    let deleteModalMock: any;

    beforeEach(() => {
      deleteModalMock = { componentInstance: {}, closed: new Subject() };
      // NgbModal is not a singleton using TestBed.inject.
      // ngbModal = TestBed.inject(NgbModal);
      ngbModal = (comp as any).modalService;
      jest.spyOn(ngbModal, 'open').mockReturnValue(deleteModalMock);
    });

    it('on confirm should call load', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(comp, 'load');

        // WHEN
        comp.delete(sampleWithRequiredData);
        deleteModalMock.closed.next('deleted');
        tick();

        // THEN
        expect(ngbModal.open).toHaveBeenCalled();
        expect(comp.load).toHaveBeenCalled();
      }),
    ));

    it('on dismiss should call load', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(comp, 'load');

        // WHEN
        comp.delete(sampleWithRequiredData);
        deleteModalMock.closed.next();
        tick();

        // THEN
        expect(ngbModal.open).toHaveBeenCalled();
        expect(comp.load).not.toHaveBeenCalled();
      }),
    ));
  });
});
