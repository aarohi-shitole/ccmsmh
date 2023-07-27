import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { HospitalService } from 'app/entities/hospital/hospital.service';
import { IHospital, Hospital } from 'app/shared/model/hospital.model';
import { HospitalCategory } from 'app/shared/model/enumerations/hospital-category.model';
import { HospitalSubCategory } from 'app/shared/model/enumerations/hospital-sub-category.model';
import { StatusInd } from 'app/shared/model/enumerations/status-ind.model';

describe('Service Tests', () => {
  describe('Hospital Service', () => {
    let injector: TestBed;
    let service: HospitalService;
    let httpMock: HttpTestingController;
    let elemDefault: IHospital;
    let expectedResult: IHospital | IHospital[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(HospitalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Hospital(
        0,
        HospitalCategory.CENTRAL,
        HospitalSubCategory.FREE,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        StatusInd.A,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastModified: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Hospital', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastModified: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastModified: currentDate,
          },
          returnedFromService
        );

        service.create(new Hospital()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Hospital', () => {
        const returnedFromService = Object.assign(
          {
            category: 'BBBBBB',
            subCategory: 'BBBBBB',
            contactNo: 'BBBBBB',
            latitude: 'BBBBBB',
            longitude: 'BBBBBB',
            docCount: 1,
            email: 'BBBBBB',
            name: 'BBBBBB',
            registrationNo: 'BBBBBB',
            address1: 'BBBBBB',
            address2: 'BBBBBB',
            area: 'BBBBBB',
            pinCode: 'BBBBBB',
            hospitalId: 1,
            odasFacilityId: 1,
            referenceNumber: 'BBBBBB',
            statusInd: 'BBBBBB',
            lastModified: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastModified: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Hospital', () => {
        const returnedFromService = Object.assign(
          {
            category: 'BBBBBB',
            subCategory: 'BBBBBB',
            contactNo: 'BBBBBB',
            latitude: 'BBBBBB',
            longitude: 'BBBBBB',
            docCount: 1,
            email: 'BBBBBB',
            name: 'BBBBBB',
            registrationNo: 'BBBBBB',
            address1: 'BBBBBB',
            address2: 'BBBBBB',
            area: 'BBBBBB',
            pinCode: 'BBBBBB',
            hospitalId: 1,
            odasFacilityId: 1,
            referenceNumber: 'BBBBBB',
            statusInd: 'BBBBBB',
            lastModified: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a Hospital', () => {
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
