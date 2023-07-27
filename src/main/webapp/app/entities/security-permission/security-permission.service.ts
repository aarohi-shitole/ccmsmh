import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISecurityPermission } from 'app/shared/model/security-permission.model';

type EntityResponseType = HttpResponse<ISecurityPermission>;
type EntityArrayResponseType = HttpResponse<ISecurityPermission[]>;

@Injectable({ providedIn: 'root' })
export class SecurityPermissionService {
  public resourceUrl = SERVER_API_URL + 'api/security-permissions';

  constructor(protected http: HttpClient) {}

  create(securityPermission: ISecurityPermission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityPermission);
    return this.http
      .post<ISecurityPermission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(securityPermission: ISecurityPermission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityPermission);
    return this.http
      .put<ISecurityPermission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISecurityPermission>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISecurityPermission[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(securityPermission: ISecurityPermission): ISecurityPermission {
    const copy: ISecurityPermission = Object.assign({}, securityPermission, {
      lastModified:
        securityPermission.lastModified && securityPermission.lastModified.isValid() ? securityPermission.lastModified.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastModified = res.body.lastModified ? moment(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((securityPermission: ISecurityPermission) => {
        securityPermission.lastModified = securityPermission.lastModified ? moment(securityPermission.lastModified) : undefined;
      });
    }
    return res;
  }
}
