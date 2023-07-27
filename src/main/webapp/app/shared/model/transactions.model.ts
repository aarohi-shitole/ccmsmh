import { Moment } from 'moment';
import { TransactionStatus } from 'app/shared/model/enumerations/transaction-status.model';

export interface ITransactions {
  id?: number;
  stockReq?: number;
  stockProvided?: number;
  status?: TransactionStatus;
  comment?: string;
  lastModified?: Moment;
  lastModifiedBy?: string;
  supplierName?: string;
  supplierId?: number;
  inventoryId?: number;
}

export class Transactions implements ITransactions {
  constructor(
    public id?: number,
    public stockReq?: number,
    public stockProvided?: number,
    public status?: TransactionStatus,
    public comment?: string,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public supplierName?: string,
    public supplierId?: number,
    public inventoryId?: number
  ) {}
}
