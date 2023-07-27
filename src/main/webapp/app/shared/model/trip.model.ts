import { Moment } from 'moment';
import { ITripDetails } from 'app/shared/model/trip-details.model';
import { TransactionStatus } from 'app/shared/model/enumerations/transaction-status.model';

export interface ITrip {
  id?: number;
  trackingNo?: string;
  mobaId?: number;
  numberPlate?: string;
  stock?: number;
  status?: TransactionStatus;
  createdDate?: Moment;
  createdBy?: string;
  lastModified?: Moment;
  lastModifiedBy?: string;
  tripDetails?: ITripDetails[];
  supplierName?: string;
  supplierId?: number;
}

export class Trip implements ITrip {
  constructor(
    public id?: number,
    public trackingNo?: string,
    public mobaId?: number,
    public numberPlate?: string,
    public stock?: number,
    public status?: TransactionStatus,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public tripDetails?: ITripDetails[],
    public supplierName?: string,
    public supplierId?: number
  ) {}
}
