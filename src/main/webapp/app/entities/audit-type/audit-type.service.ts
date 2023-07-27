import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAuditType } from 'app/shared/model/audit-type.model';

type EntityResponseType = HttpResponse<IAuditType>;
type EntityArrayResponseType = HttpResponse<IAuditType[]>;

@Injectable({ providedIn: 'root' })
export class AuditTypeService {
  public resourceUrl = SERVER_API_URL + 'api/audit-types';

  constructor(protected http: HttpClient) {}

  create(auditType: IAuditType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditType);
    return this.http
      .post<IAuditType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(auditType: IAuditType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditType);
    return this.http
      .put<IAuditType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAuditType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuditType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(auditType: IAuditType): IAuditType {
    const copy: IAuditType = Object.assign({}, auditType, {
      lastModified: auditType.lastModified && auditType.lastModified.isValid() ? auditType.lastModified.toJSON() : undefined,
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
      res.body.forEach((auditType: IAuditType) => {
        auditType.lastModified = auditType.lastModified ? moment(auditType.lastModified) : undefined;
      });
    }
    return res;
  }
}
