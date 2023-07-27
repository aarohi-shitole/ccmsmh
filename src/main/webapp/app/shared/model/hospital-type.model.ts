import { Moment } from 'moment';

export interface IHospitalType {
  id?: number;
  name?: string;
  desciption?: string;
  deleted?: boolean;
  lastModified?: Moment;
  lastModifiedBy?: string;
}

export class HospitalType implements IHospitalType {
  constructor(
    public id?: number,
    public name?: string,
    public desciption?: string,
    public deleted?: boolean,
    public lastModified?: Moment,
    public lastModifiedBy?: string
  ) {
    this.deleted = this.deleted || false;
  }
}
