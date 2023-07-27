import { Moment } from 'moment';

export interface IState {
  id?: number;
  name?: string;
  deleted?: boolean;
  lgdCode?: number;
  lastModified?: Moment;
  lastModifiedBy?: string;
}

export class State implements IState {
  constructor(
    public id?: number,
    public name?: string,
    public deleted?: boolean,
    public lgdCode?: number,
    public lastModified?: Moment,
    public lastModifiedBy?: string
  ) {
    this.deleted = this.deleted || false;
  }
}
