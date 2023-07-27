import { Moment } from 'moment';

export interface IInventory {
  id?: number;
  stock?: number;
  capcity?: number;
  installedCapcity?: number;
  lastModified?: Moment;
  lastModifiedBy?: string;
  inventoryMasterName?: string;
  inventoryMasterId?: number;
  supplierName?: string;
  supplierId?: number;
  hospitalName?: string;
  hospitalId?: number;
}

export class Inventory implements IInventory {
  constructor(
    public id?: number,
    public stock?: number,
    public capcity?: number,
    public installedCapcity?: number,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public inventoryMasterName?: string,
    public inventoryMasterId?: number,
    public supplierName?: string,
    public supplierId?: number,
    public hospitalName?: string,
    public hospitalId?: number
  ) {}
}
