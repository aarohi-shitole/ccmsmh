import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SupplierService } from 'app/entities/supplier/supplier.service';
import { ISupplier, Supplier } from 'app/shared/model/supplier.model';
import { SupplierType } from 'app/shared/model/enumerations/supplier-type.model';
import { StatusInd } from 'app/shared/model/enumerations/status-ind.model';

describe('Service Tests', () => {
  describe('Supplier Service', () => {
    let injector: TestBed;
    let service: SupplierService;
    let httpMock: HttpTestingController;
    let elemDefault: ISupplier;
    let expectedResult: ISupplier | ISupplier[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SupplierService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Supplier(
        0,
        'AAAAAAA',
        SupplierType.REFILLER,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
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

      it('should create a Supplier', () => {
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

        service.create(new Supplier()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Supplier', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            supplierType: 'BBBBBB',
            contactNo: 'BBBBBB',
            latitude: 'BBBBBB',
            longitude: 'BBBBBB',
            email: 'BBBBBB',
            registrationNo: 'BBBBBB',
            address1: 'BBBBBB',
            address2: 'BBBBBB',
            area: 'BBBBBB',
            pinCode: 'BBBBBB',
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

      it('should return a list of Supplier', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            supplierType: 'BBBBBB',
            contactNo: 'BBBBBB',
            latitude: 'BBBBBB',
            longitude: 'BBBBBB',
            email: 'BBBBBB',
            registrationNo: 'BBBBBB',
            address1: 'BBBBBB',
            address2: 'BBBBBB',
            area: 'BBBBBB',
            pinCode: 'BBBBBB',
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

      it('should delete a Supplier', () => {
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
