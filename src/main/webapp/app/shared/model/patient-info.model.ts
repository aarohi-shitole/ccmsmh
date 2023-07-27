import { Moment } from 'moment';

export interface IPatientInfo {
  id?: number;
  icmrId?: string;
  srfId?: string;
  labName?: string;
  patientID?: string;
  patientName?: string;
  age?: string;
  ageIn?: string;
  gender?: string;
  nationality?: string;
  address?: string;
  villageTown?: string;
  pincode?: string;
  patientCategory?: string;
  dateOfSampleCollection?: Moment;
  dateOfSampleReceived?: Moment;
  sampleType?: string;
  sampleId?: string;
  underlyingMedicalCondition?: string;
  hospitalized?: string;
  hospitalName?: string;
  hospitalizationDate?: Moment;
  hospitalState?: string;
  hospitalDistrict?: string;
  symptomsStatus?: string;
  symptoms?: string;
  testingKitUsed?: string;
  eGeneNGene?: string;
  ctValueOfEGeneNGene?: string;
  rdRpSGene?: string;
  ctValueOfRdRpSGene?: string;
  oRF1aORF1bNN2Gene?: string;
  ctValueOfORF1aORF1bNN2Gene?: string;
  repeatSample?: string;
  dateOfSampleTested?: Moment;
  entryDate?: Moment;
  confirmationDate?: Moment;
  finalResultSample?: string;
  remarks?: string;
  editedOn?: Moment;
  ccmsPullDate?: Moment;
  lastModified?: Moment;
  lastModifiedBy?: string;
  stateName?: string;
  stateId?: number;
  districtName?: string;
  districtId?: number;
}

export class PatientInfo implements IPatientInfo {
  constructor(
    public id?: number,
    public icmrId?: string,
    public srfId?: string,
    public labName?: string,
    public patientID?: string,
    public patientName?: string,
    public age?: string,
    public ageIn?: string,
    public gender?: string,
    public nationality?: string,
    public address?: string,
    public villageTown?: string,
    public pincode?: string,
    public patientCategory?: string,
    public dateOfSampleCollection?: Moment,
    public dateOfSampleReceived?: Moment,
    public sampleType?: string,
    public sampleId?: string,
    public underlyingMedicalCondition?: string,
    public hospitalized?: string,
    public hospitalName?: string,
    public hospitalizationDate?: Moment,
    public hospitalState?: string,
    public hospitalDistrict?: string,
    public symptomsStatus?: string,
    public symptoms?: string,
    public testingKitUsed?: string,
    public eGeneNGene?: string,
    public ctValueOfEGeneNGene?: string,
    public rdRpSGene?: string,
    public ctValueOfRdRpSGene?: string,
    public oRF1aORF1bNN2Gene?: string,
    public ctValueOfORF1aORF1bNN2Gene?: string,
    public repeatSample?: string,
    public dateOfSampleTested?: Moment,
    public entryDate?: Moment,
    public confirmationDate?: Moment,
    public finalResultSample?: string,
    public remarks?: string,
    public editedOn?: Moment,
    public ccmsPullDate?: Moment,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public stateName?: string,
    public stateId?: number,
    public districtName?: string,
    public districtId?: number
  ) {}
}
