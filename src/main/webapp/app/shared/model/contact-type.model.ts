import { Moment } from 'moment';

export interface IContactType {
  id?: number;
  name?: string;
  deleted?: boolean;
  lastModified?: Moment;
  lastModifiedBy?: string;
}

export class ContactType implements IContactType {
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
