import { Moment } from 'moment';

export interface IAuditSystem {
  id?: number;
  auditorName?: string;
  defectCount?: number;
  defectFixCount?: number;
  inspectionDate?: Moment;
  remark?: string;
  status?: string;
  lastModified?: Moment;
  lastModifiedBy?: string;
  hospitalName?: string;
  hospitalId?: number;
  supplierName?: string;
  supplierId?: number;
  auditTypeName?: string;
  auditTypeId?: number;
}

export class AuditSystem implements IAuditSystem {
  constructor(
    public id?: number,
    public auditorName?: string,
    public defectCount?: number,
    public defectFixCount?: number,
    public inspectionDate?: Moment,
    public remark?: string,
    public status?: string,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public hospitalName?: string,
    public hospitalId?: number,
    public supplierName?: string,
    public supplierId?: number,
    public auditTypeName?: string,
    public auditTypeId?: number
  ) {}
}
