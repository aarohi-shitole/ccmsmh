import { Moment } from 'moment';

export interface IBedTransactions {
  id?: number;
  occupied?: number;
  onCylinder?: number;
  onLMO?: number;
  onConcentrators?: number;
  lastModified?: Moment;
  lastModifiedBy?: string;
  bedTypeName?: string;
  bedTypeId?: number;
  hospitalName?: string;
  hospitalId?: number;
}

export class BedTransactions implements IBedTransactions {
  constructor(
    public id?: number,
    public occupied?: number,
    public onCylinder?: number,
    public onLMO?: number,
    public onConcentrators?: number,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public bedTypeName?: string,
    public bedTypeId?: number,
    public hospitalName?: string,
    public hospitalId?: number
  ) {}
}
