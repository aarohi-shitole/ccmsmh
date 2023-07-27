import { Moment } from 'moment';
import { ISecurityPermission } from 'app/shared/model/security-permission.model';
import { ISecurityRole } from 'app/shared/model/security-role.model';

export interface ISecurityUser {
  id?: number;
  firstName?: string;
  lastName?: string;
  designation?: string;
  login?: string;
  passwordHash?: string;
  email?: string;
  imageUrl?: string;
  activated?: boolean;
  langKey?: string;
  activationKey?: string;
  resetKey?: string;
  resetDate?: Moment;
  mobileNo?: string;
  oneTimePassword?: string;
  otpExpiryTime?: Moment;
  lastModified?: Moment;
  lastModifiedBy?: string;
  securityPermissions?: ISecurityPermission[];
  securityRoles?: ISecurityRole[];
}

export class SecurityUser implements ISecurityUser {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public designation?: string,
    public login?: string,
    public passwordHash?: string,
    public email?: string,
    public imageUrl?: string,
    public activated?: boolean,
    public langKey?: string,
    public activationKey?: string,
    public resetKey?: string,
    public resetDate?: Moment,
    public mobileNo?: string,
    public oneTimePassword?: string,
    public otpExpiryTime?: Moment,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public securityPermissions?: ISecurityPermission[],
    public securityRoles?: ISecurityRole[]
  ) {
    this.activated = this.activated || false;
  }
}
