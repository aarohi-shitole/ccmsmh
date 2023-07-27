import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAuditSystem } from 'app/shared/model/audit-system.model';

type EntityResponseType = HttpResponse<IAuditSystem>;
type EntityArrayResponseType = HttpResponse<IAuditSystem[]>;

@Injectable({ providedIn: 'root' })
export class AuditSystemService {
  public resourceUrl = SERVER_API_URL + 'api/audit-systems';

  constructor(protected http: HttpClient) {}

  create(auditSystem: IAuditSystem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditSystem);
    return this.http
      .post<IAuditSystem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(auditSystem: IAuditSystem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditSystem);
    return this.http
      .put<IAuditSystem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAuditSystem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuditSystem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(auditSystem: IAuditSystem): IAuditSystem {
    const copy: IAuditSystem = Object.assign({}, auditSystem, {
      inspectionDate: auditSystem.inspectionDate && auditSystem.inspectionDate.isValid() ? auditSystem.inspectionDate.toJSON() : undefined,
      lastModified: auditSystem.lastModified && auditSystem.lastModified.isValid() ? auditSystem.lastModified.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inspectionDate = res.body.inspectionDate ? moment(res.body.inspectionDate) : undefined;
      res.body.lastModified = res.body.lastModified ? moment(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((auditSystem: IAuditSystem) => {
        auditSystem.inspectionDate = auditSystem.inspectionDate ? moment(auditSystem.inspectionDate) : undefined;
        auditSystem.lastModified = auditSystem.lastModified ? moment(auditSystem.lastModified) : undefined;
      });
    }
    return res;
  }
}
