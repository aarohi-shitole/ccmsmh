import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISecurityRole } from 'app/shared/model/security-role.model';

type EntityResponseType = HttpResponse<ISecurityRole>;
type EntityArrayResponseType = HttpResponse<ISecurityRole[]>;

@Injectable({ providedIn: 'root' })
export class SecurityRoleService {
  public resourceUrl = SERVER_API_URL + 'api/security-roles';

  constructor(protected http: HttpClient) {}

  create(securityRole: ISecurityRole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityRole);
    return this.http
      .post<ISecurityRole>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(securityRole: ISecurityRole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(securityRole);
    return this.http
      .put<ISecurityRole>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISecurityRole>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISecurityRole[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(securityRole: ISecurityRole): ISecurityRole {
    const copy: ISecurityRole = Object.assign({}, securityRole, {
      lastModified: securityRole.lastModified && securityRole.lastModified.isValid() ? securityRole.lastModified.toJSON() : undefined,
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
      res.body.forEach((securityRole: ISecurityRole) => {
        securityRole.lastModified = securityRole.lastModified ? moment(securityRole.lastModified) : undefined;
      });
    }
    return res;
  }
}
