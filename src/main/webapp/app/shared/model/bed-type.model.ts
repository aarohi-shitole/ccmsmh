import { Moment } from 'moment';

export interface IBedType {
  id?: number;
  name?: string;
  perDayOX?: number;
  deleted?: boolean;
  lastModified?: Moment;
  lastModifiedBy?: string;
}

export class BedType implements IBedType {
  constructor(
    public id?: number,
    public name?: string,
    public perDayOX?: number,
    public deleted?: boolean,
    public lastModified?: Moment,
    public lastModifiedBy?: string
  ) {
    this.deleted = this.deleted || false;
  }
}
