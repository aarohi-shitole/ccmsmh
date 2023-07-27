import { Moment } from 'moment';

export interface IInventoryUsed {
  id?: number;
  stock?: number;
  capcity?: number;
  comment?: string;
  lastModified?: Moment;
  lastModifiedBy?: string;
  inventoryId?: number;
}

export class InventoryUsed implements IInventoryUsed {
  constructor(
    public id?: number,
    public stock?: number,
    public capcity?: number,
    public comment?: string,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public inventoryId?: number
  ) {}
}
