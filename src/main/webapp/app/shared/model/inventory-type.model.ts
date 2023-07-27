import { Moment } from 'moment';

export interface IInventoryType {
  id?: number;
  name?: string;
  deleted?: boolean;
  lastModified?: Moment;
  lastModifiedBy?: string;
}

export class InventoryType implements IInventoryType {
  constructor(
    public id?: number,
    public name?: string,
    public deleted?: boolean,
    public lastModified?: Moment,
    public lastModifiedBy?: string
  ) {
    this.deleted = this.deleted || false;
  }
}
