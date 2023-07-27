import { Moment } from 'moment';
import { IHospital } from 'app/shared/model/hospital.model';
import { SupplierType } from 'app/shared/model/enumerations/supplier-type.model';
import { StatusInd } from 'app/shared/model/enumerations/status-ind.model';

export interface ISupplier {
  id?: number;
  name?: string;
  supplierType?: SupplierType;
  contactNo?: string;
  latitude?: string;
  longitude?: string;
  email?: string;
  registrationNo?: string;
  address1?: string;
  address2?: string;
  area?: string;
  pinCode?: string;
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
  inventoryTypeName?: string;
  inventoryTypeId?: number;
  hospitals?: IHospital[];
}

export class Supplier implements ISupplier {
  constructor(
    public id?: number,
    public name?: string,
    public supplierType?: SupplierType,
    public contactNo?: string,
    public latitude?: string,
    public longitude?: string,
    public email?: string,
    public registrationNo?: string,
    public address1?: string,
    public address2?: string,
    public area?: string,
    public pinCode?: string,
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
    public inventoryTypeName?: string,
    public inventoryTypeId?: number,
    public hospitals?: IHospital[]
  ) {}
}
