import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PatientInfoService } from 'app/entities/patient-info/patient-info.service';
import { IPatientInfo, PatientInfo } from 'app/shared/model/patient-info.model';

describe('Service Tests', () => {
  describe('PatientInfo Service', () => {
    let injector: TestBed;
    let service: PatientInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPatientInfo;
    let expectedResult: IPatientInfo | IPatientInfo[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PatientInfoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PatientInfo(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfSampleCollection: currentDate.format(DATE_TIME_FORMAT),
            dateOfSampleReceived: currentDate.format(DATE_TIME_FORMAT),
            hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
            dateOfSampleTested: currentDate.format(DATE_TIME_FORMAT),
            entryDate: currentDate.format(DATE_TIME_FORMAT),
            confirmationDate: currentDate.format(DATE_TIME_FORMAT),
            editedOn: currentDate.format(DATE_TIME_FORMAT),
            ccmsPullDate: currentDate.format(DATE_TIME_FORMAT),
            lastModified: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PatientInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfSampleCollection: currentDate.format(DATE_TIME_FORMAT),
            dateOfSampleReceived: currentDate.format(DATE_TIME_FORMAT),
            hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
            dateOfSampleTested: currentDate.format(DATE_TIME_FORMAT),
            entryDate: currentDate.format(DATE_TIME_FORMAT),
            confirmationDate: currentDate.format(DATE_TIME_FORMAT),
            editedOn: currentDate.format(DATE_TIME_FORMAT),
            ccmsPullDate: currentDate.format(DATE_TIME_FORMAT),
            lastModified: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfSampleCollection: currentDate,
            dateOfSampleReceived: currentDate,
            hospitalizationDate: currentDate,
            dateOfSampleTested: currentDate,
            entryDate: currentDate,
            confirmationDate: currentDate,
            editedOn: currentDate,
            ccmsPullDate: currentDate,
            lastModified: currentDate,
          },
          returnedFromService
        );

        service.create(new PatientInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PatientInfo', () => {
        const returnedFromService = Object.assign(
          {
            icmrId: 'BBBBBB',
            srfId: 'BBBBBB',
            labName: 'BBBBBB',
            patientID: 'BBBBBB',
            patientName: 'BBBBBB',
            age: 'BBBBBB',
            ageIn: 'BBBBBB',
            gender: 'BBBBBB',
            nationality: 'BBBBBB',
            address: 'BBBBBB',
            villageTown: 'BBBBBB',
            pincode: 'BBBBBB',
            patientCategory: 'BBBBBB',
            dateOfSampleCollection: currentDate.format(DATE_TIME_FORMAT),
            dateOfSampleReceived: currentDate.format(DATE_TIME_FORMAT),
            sampleType: 'BBBBBB',
            sampleId: 'BBBBBB',
            underlyingMedicalCondition: 'BBBBBB',
            hospitalized: 'BBBBBB',
            hospitalName: 'BBBBBB',
            hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
            hospitalState: 'BBBBBB',
            hospitalDistrict: 'BBBBBB',
            symptomsStatus: 'BBBBBB',
            symptoms: 'BBBBBB',
            testingKitUsed: 'BBBBBB',
            eGeneNGene: 'BBBBBB',
            ctValueOfEGeneNGene: 'BBBBBB',
            rdRpSGene: 'BBBBBB',
            ctValueOfRdRpSGene: 'BBBBBB',
            oRF1aORF1bNN2Gene: 'BBBBBB',
            ctValueOfORF1aORF1bNN2Gene: 'BBBBBB',
            repeatSample: 'BBBBBB',
            dateOfSampleTested: currentDate.format(DATE_TIME_FORMAT),
            entryDate: currentDate.format(DATE_TIME_FORMAT),
            confirmationDate: currentDate.format(DATE_TIME_FORMAT),
            finalResultSample: 'BBBBBB',
            remarks: 'BBBBBB',
            editedOn: currentDate.format(DATE_TIME_FORMAT),
            ccmsPullDate: currentDate.format(DATE_TIME_FORMAT),
            lastModified: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfSampleCollection: currentDate,
            dateOfSampleReceived: currentDate,
            hospitalizationDate: currentDate,
            dateOfSampleTested: currentDate,
            entryDate: currentDate,
            confirmationDate: currentDate,
            editedOn: currentDate,
            ccmsPullDate: currentDate,
            lastModified: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PatientInfo', () => {
        const returnedFromService = Object.assign(
          {
            icmrId: 'BBBBBB',
            srfId: 'BBBBBB',
            labName: 'BBBBBB',
            patientID: 'BBBBBB',
            patientName: 'BBBBBB',
            age: 'BBBBBB',
            ageIn: 'BBBBBB',
            gender: 'BBBBBB',
            nationality: 'BBBBBB',
            address: 'BBBBBB',
            villageTown: 'BBBBBB',
            pincode: 'BBBBBB',
            patientCategory: 'BBBBBB',
            dateOfSampleCollection: currentDate.format(DATE_TIME_FORMAT),
            dateOfSampleReceived: currentDate.format(DATE_TIME_FORMAT),
            sampleType: 'BBBBBB',
            sampleId: 'BBBBBB',
            underlyingMedicalCondition: 'BBBBBB',
            hospitalized: 'BBBBBB',
            hospitalName: 'BBBBBB',
            hospitalizationDate: currentDate.format(DATE_TIME_FORMAT),
            hospitalState: 'BBBBBB',
            hospitalDistrict: 'BBBBBB',
            symptomsStatus: 'BBBBBB',
            symptoms: 'BBBBBB',
            testingKitUsed: 'BBBBBB',
            eGeneNGene: 'BBBBBB',
            ctValueOfEGeneNGene: 'BBBBBB',
            rdRpSGene: 'BBBBBB',
            ctValueOfRdRpSGene: 'BBBBBB',
            oRF1aORF1bNN2Gene: 'BBBBBB',
            ctValueOfORF1aORF1bNN2Gene: 'BBBBBB',
            repeatSample: 'BBBBBB',
            dateOfSampleTested: currentDate.format(DATE_TIME_FORMAT),
            entryDate: currentDate.format(DATE_TIME_FORMAT),
            confirmationDate: currentDate.format(DATE_TIME_FORMAT),
            finalResultSample: 'BBBBBB',
            remarks: 'BBBBBB',
            editedOn: currentDate.format(DATE_TIME_FORMAT),
            ccmsPullDate: currentDate.format(DATE_TIME_FORMAT),
            lastModified: currentDate.format(DATE_TIME_FORMAT),
            lastModifiedBy: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfSampleCollection: currentDate,
            dateOfSampleReceived: currentDate,
            hospitalizationDate: currentDate,
            dateOfSampleTested: currentDate,
            entryDate: currentDate,
            confirmationDate: currentDate,
            editedOn: currentDate,
            ccmsPullDate: currentDate,
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

      it('should delete a PatientInfo', () => {
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
