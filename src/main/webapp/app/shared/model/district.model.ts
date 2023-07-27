import { Moment } from 'moment';

export interface IDistrict {
  id?: number;
  name?: string;
  deleted?: boolean;
  lgdCode?: number;
  lastModified?: Moment;
  lastModifiedBy?: string;
  stateName?: string;
  stateId?: number;
  divisionName?: string;
  divisionId?: number;
}

export class District implements IDistrict {
  constructor(
    public id?: number,
    public name?: string,
    public deleted?: boolean,
    public lgdCode?: number,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public stateName?: string,
    public stateId?: number,
    public divisionName?: string,
    public divisionId?: number
  ) {
    this.deleted = this.deleted || false;
  }
}
