import { Moment } from 'moment';

export interface ITaluka {
  id?: number;
  name?: string;
  deleted?: boolean;
  lgdCode?: number;
  lastModified?: Moment;
  lastModifiedBy?: string;
  districtName?: string;
  districtId?: number;
}

export class Taluka implements ITaluka {
  constructor(
    public id?: number,
    public name?: string,
    public deleted?: boolean,
    public lgdCode?: number,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public districtName?: string,
    public districtId?: number
  ) {
    this.deleted = this.deleted || false;
  }
}
