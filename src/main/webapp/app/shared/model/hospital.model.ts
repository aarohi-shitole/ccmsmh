import { Moment } from 'moment';
import { ISupplier } from 'app/shared/model/supplier.model';
import { HospitalCategory } from 'app/shared/model/enumerations/hospital-category.model';
import { HospitalSubCategory } from 'app/shared/model/enumerations/hospital-sub-category.model';
import { StatusInd } from 'app/shared/model/enumerations/status-ind.model';

export interface IHospital {
  id?: number;
  category?: HospitalCategory;
  subCategory?: HospitalSubCategory;
  contactNo?: string;
  latitude?: string;
  longitude?: string;
  docCount?: number;
  email?: string;
  name?: string;
  registrationNo?: string;
  address1?: string;
  address2?: string;
  area?: string;
  pinCode?: string;
  hospitalId?: number;
  odasFacilityId?: number;
  referenceNumber?: string;
  statusInd?: StatusInd;
  lastModified?: Moment;
  lastModifiedBy?: string;
  stateName?: string;
  stateId?: number;
  districtName?: string;
  districtId?: number;
  talukaName?: string;
  talukaId?: number;
  cityName?: string;
  cityId?: number;
  municipalCorpName?: string;
  municipalCorpId?: number;
  hospitalTypeName?: string;
  hospitalTypeId?: number;
  suppliers?: ISupplier[];
}

export class Hospital implements IHospital {
  constructor(
    public id?: number,
    public category?: HospitalCategory,
    public subCategory?: HospitalSubCategory,
    public contactNo?: string,
    public latitude?: string,
    public longitude?: string,
    public docCount?: number,
    public email?: string,
    public name?: string,
    public registrationNo?: string,
    public address1?: string,
    public address2?: string,
    public area?: string,
    public pinCode?: string,
    public hospitalId?: number,
    public odasFacilityId?: number,
    public referenceNumber?: string,
    public statusInd?: StatusInd,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public stateName?: string,
    public stateId?: number,
    public districtName?: string,
    public districtId?: number,
    public talukaName?: string,
    public talukaId?: number,
    public cityName?: string,
    public cityId?: number,
    public municipalCorpName?: string,
    public municipalCorpId?: number,
    public hospitalTypeName?: string,
    public hospitalTypeId?: number,
    public suppliers?: ISupplier[]
  ) {}
}
