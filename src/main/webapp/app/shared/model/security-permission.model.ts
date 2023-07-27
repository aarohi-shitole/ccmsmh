import { Moment } from 'moment';
import { ISecurityRole } from 'app/shared/model/security-role.model';
import { ISecurityUser } from 'app/shared/model/security-user.model';

export interface ISecurityPermission {
  id?: number;
  name?: string;
  description?: string;
  lastModified?: Moment;
  lastModifiedBy?: string;
  securityRoles?: ISecurityRole[];
  securityUsers?: ISecurityUser[];
}

export class SecurityPermission implements ISecurityPermission {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public securityRoles?: ISecurityRole[],
    public securityUsers?: ISecurityUser[]
  ) {}
}
