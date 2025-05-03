import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpHeaders, HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subject, of } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { sampleWithRequiredData } from '../add-ons-selected-by-organization.test-samples';
import { AddOnsSelectedByOrganizationService } from '../service/add-ons-selected-by-organization.service';

import { AddOnsSelectedByOrganizationComponent } from './add-ons-selected-by-organization.component';
import SpyInstance = jest.SpyInstance;

describe('AddOnsSelectedByOrganization Management Component', () => {
  let comp: AddOnsSelectedByOrganizationComponent;
  let fixture: ComponentFixture<AddOnsSelectedByOrganizationComponent>;
  let service: AddOnsSelectedByOrganizationService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AddOnsSelectedByOrganizationComponent],
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'organizationId,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'organizationId,desc',
              }),
            ),
            snapshot: {
              queryParams: {},
              queryParamMap: jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'organizationId,desc',
              }),
            },
          },
        },
      ],
    })
      .overrideTemplate(AddOnsSelectedByOrganizationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AddOnsSelectedByOrganizationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AddOnsSelectedByOrganizationService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    jest
      .spyOn(service, 'query')
      .mockReturnValueOnce(
        of(
          new HttpResponse({
            body: [{ organizationId: '5d2569e7-278f-4572-8beb-b3497c54caf8' }],
            headers: new HttpHeaders({
              link: '<http://localhost/api/foo?page=1&size=20>; rel="next"',
            }),
          }),
        ),
      )
      .mockReturnValueOnce(
        of(
          new HttpResponse({
            body: [{ organizationId: '327f5417-09c8-4f5c-b313-117201e1d0b5' }],
            headers: new HttpHeaders({
              link: '<http://localhost/api/foo?page=0&size=20>; rel="prev",<http://localhost/api/foo?page=2&size=20>; rel="next"',
            }),
          }),
        ),
      );
  });

  it('should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.addOnsSelectedByOrganizations()[0]).toEqual(
      expect.objectContaining({ organizationId: '5d2569e7-278f-4572-8beb-b3497c54caf8' }),
    );
  });

  describe('trackOrganizationId', () => {
    it('should forward to addOnsSelectedByOrganizationService', () => {
      const entity = { organizationId: '5d2569e7-278f-4572-8beb-b3497c54caf8' };
      jest.spyOn(service, 'getAddOnsSelectedByOrganizationIdentifier');
      const organizationId = comp.trackOrganizationId(entity);
      expect(service.getAddOnsSelectedByOrganizationIdentifier).toHaveBeenCalledWith(entity);
      expect(organizationId).toBe(entity.organizationId);
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
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['organizationId,desc'] }));
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
