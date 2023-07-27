import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AuditSystemService } from 'app/entities/audit-system/audit-system.service';
import { IAuditSystem, AuditSystem } from 'app/shared/model/audit-system.model';

describe('Service Tests', () => {
  describe('AuditSystem Service', () => {
    let injector: TestBed;
    let service: AuditSystemService;
    let httpMock: HttpTestingController;
    let elemDefault: IAuditSystem;
    let expectedResult: IAuditSystem | IAuditSystem[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AuditSystemService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AuditSystem(0, 'AAAAAAA', 0, 0, currentDate, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            inspectionDate: currentDate.format(DATE_TIME_FORMAT),
            lastModified: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AuditSystem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            inspectionDate: currentDate.format(DATE_TIME_FORMAT),
            lastModified: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inspectionDate: currentDate,
            lastModified: currentDate,
          },
          returnedFromService
        );

        service.create(new AuditSystem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AuditSystem', () => {
        const returnedFromService = Object.assign(
          {
            auditorName: 'BBBBBB',
            defectCount: 1,
            defectFixCount: 1,
            inspectionDate: currentDate.format(DATE_TIME_FORMAT),
            remark: 'BBBBBB',
            status: 'BBBBBB',
            lastModified: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inspectionDate: currentDate,
            lastModified: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AuditSystem', () => {
        const returnedFromService = Object.assign(
          {
            auditorName: 'BBBBBB',
            defectCount: 1,
            defectFixCount: 1,
            inspectionDate: currentDate.format(DATE_TIME_FORMAT),
            remark: 'BBBBBB',
            status: 'BBBBBB',
            lastModified: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inspectionDate: currentDate,
            lastModified: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AuditSystem', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
