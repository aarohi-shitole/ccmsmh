import { Moment } from 'moment';

export interface ICity {
  id?: number;
  name?: string;
  deleted?: boolean;
  lgdCode?: number;
  lastModified?: Moment;
  lastModifiedBy?: string;
  talukaName?: string;
  talukaId?: number;
}

export class City implements ICity {
  constructor(
    public id?: number,
    public name?: string,
    public deleted?: boolean,
    public lgdCode?: number,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public talukaName?: string,
    public talukaId?: number
  ) {
    this.deleted = this.deleted || false;
  }
}
