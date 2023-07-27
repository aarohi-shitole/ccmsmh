import { Moment } from 'moment';

export interface IContact {
  id?: number;
  name?: string;
  contactNo?: string;
  email?: string;
  lastModified?: Moment;
  lastModifiedBy?: string;
  contactTypeName?: string;
  contactTypeId?: number;
  hospitalName?: string;
  hospitalId?: number;
  supplierName?: string;
  supplierId?: number;
}

export class Contact implements IContact {
  constructor(
    public id?: number,
    public name?: string,
    public contactNo?: string,
    public email?: string,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public contactTypeName?: string,
    public contactTypeId?: number,
    public hospitalName?: string,
    public hospitalId?: number,
    public supplierName?: string,
    public supplierId?: number
  ) {}
}
