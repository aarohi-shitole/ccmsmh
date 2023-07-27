import { Moment } from 'moment';

export interface IAuditType {
  id?: number;
  name?: string;
  deleted?: boolean;
  lastModified?: Moment;
  lastModifiedBy?: string;
}

export class AuditType implements IAuditType {
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
