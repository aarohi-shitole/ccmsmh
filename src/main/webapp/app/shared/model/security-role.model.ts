import { Moment } from 'moment';
import { ISecurityPermission } from 'app/shared/model/security-permission.model';
import { ISecurityUser } from 'app/shared/model/security-user.model';

export interface ISecurityRole {
  id?: number;
  name?: string;
  description?: string;
  lastModified?: Moment;
  lastModifiedBy?: string;
  securityPermissions?: ISecurityPermission[];
  securityUsers?: ISecurityUser[];
}

export class SecurityRole implements ISecurityRole {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public securityPermissions?: ISecurityPermission[],
    public securityUsers?: ISecurityUser[]
  ) {}
}
