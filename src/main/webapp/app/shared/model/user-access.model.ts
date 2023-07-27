import { Moment } from 'moment';
import { AccessLevel } from 'app/shared/model/enumerations/access-level.model';

export interface IUserAccess {
  id?: number;
  level?: AccessLevel;
  accessId?: number;
  lastModified?: Moment;
  lastModifiedBy?: string;
  securityUserLogin?: string;
  securityUserId?: number;
}

export class UserAccess implements IUserAccess {
  constructor(
    public id?: number,
    public level?: AccessLevel,
    public accessId?: number,
    public lastModified?: Moment,
    public lastModifiedBy?: string,
    public securityUserLogin?: string,
    public securityUserId?: number
  ) {}
}
