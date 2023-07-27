import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SecurityUserService } from 'app/entities/security-user/security-user.service';
import { ISecurityUser, SecurityUser } from 'app/shared/model/security-user.model';

describe('Service Tests', () => {
  describe('SecurityUser Service', () => {
    let injector: TestBed;
    let service: SecurityUserService;
    let httpMock: HttpTestingController;
    let elemDefault: ISecurityUser;
    let expectedResult: ISecurityUser | ISecurityUser[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SecurityUserService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SecurityUser(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            resetDate: currentDate.format(DATE_TIME_FORMAT),
            otpExpiryTime: currentDate.format(DATE_TIME_FORMAT),
            lastModified: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SecurityUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            resetDate: currentDate.format(DATE_TIME_FORMAT),
            otpExpiryTime: currentDate.format(DATE_TIME_FORMAT),
            lastModified: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            resetDate: currentDate,
            otpExpiryTime: currentDate,
            lastModified: currentDate,
          },
          returnedFromService
        );

        service.create(new SecurityUser()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SecurityUser', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            designation: 'BBBBBB',
            login: 'BBBBBB',
            passwordHash: 'BBBBBB',
            email: 'BBBBBB',
            imageUrl: 'BBBBBB',
            activated: true,
            langKey: 'BBBBBB',
            activationKey: 'BBBBBB',
            resetKey: 'BBBBBB',
            resetDate: currentDate.format(DATE_TIME_FORMAT),
            mobileNo: 'BBBBBB',
            oneTimePassword: 'BBBBBB',
            otpExpiryTime: currentDate.format(DATE_TIME_FORMAT),
            lastModified: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            resetDate: currentDate,
            otpExpiryTime: currentDate,
            lastModified: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SecurityUser', () => {
        const returnedFromService = Object.assign(
          {
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            designation: 'BBBBBB',
            login: 'BBBBBB',
            passwordHash: 'BBBBBB',
            email: 'BBBBBB',
            imageUrl: 'BBBBBB',
            activated: true,
            langKey: 'BBBBBB',
            activationKey: 'BBBBBB',
            resetKey: 'BBBBBB',
            resetDate: currentDate.format(DATE_TIME_FORMAT),
            mobileNo: 'BBBBBB',
            oneTimePassword: 'BBBBBB',
            otpExpiryTime: currentDate.format(DATE_TIME_FORMAT),
            lastModified: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            resetDate: currentDate,
            otpExpiryTime: currentDate,
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

      it('should delete a SecurityUser', () => {
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
