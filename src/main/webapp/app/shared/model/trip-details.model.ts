import { Moment } from 'moment';
import { TransactionStatus } from 'app/shared/model/enumerations/transaction-status.model';

export interface ITripDetails {
  id?: number;
  stockSent?: number;
  stockRec?: number;
  status?: TransactionStatus;
  createdDate?: Moment;
  createdBy?: string;
  lastModified?: Moment;
  lastModifiedBy?: string;
  supplierName?: string;
  supplierId?: number;
  hospitalName?: string;
  hospitalId?: number;
  transactionsId?: number;
  tripId?: number;
}

export class TripDetails implements ITripDetails {
  constructor(
    public id?: number,
    public stockSent?: number,
    public stockRec?: number,
    public status?: TransactionStatus,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public supplierName?: string,
    public supplierId?: number,
    public hospitalName?: string,
    public hospitalId?: number,
    public transactionsId?: number,
    public tripId?: number
  ) {}
}
