import { Moment } from 'moment';

export interface IInventoryMaster {
  id?: number;
  name?: string;
  description?: string;
  volume?: number;
  unit?: string;
  calulateVolume?: number;
  dimensions?: string;
  subTypeInd?: string;
  deleted?: boolean;
  lastModified?: Moment;
  lastModifiedBy?: string;
  inventoryTypeName?: string;
  inventoryTypeId?: number;
}

export class InventoryMaster implements IInventoryMaster {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public volume?: number,
    public unit?: string,
    public calulateVolume?: number,
    public dimensions?: string,
    public subTypeInd?: string,
    public deleted?: boolean,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public inventoryTypeName?: string,
    public inventoryTypeId?: number
  ) {
    this.deleted = this.deleted || false;
  }
}
