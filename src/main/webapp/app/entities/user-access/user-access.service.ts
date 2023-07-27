import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserAccess } from 'app/shared/model/user-access.model';

type EntityResponseType = HttpResponse<IUserAccess>;
type EntityArrayResponseType = HttpResponse<IUserAccess[]>;

@Injectable({ providedIn: 'root' })
export class UserAccessService {
  public resourceUrl = SERVER_API_URL + 'api/user-accesses';

  constructor(protected http: HttpClient) {}

  create(userAccess: IUserAccess): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userAccess);
    return this.http
      .post<IUserAccess>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userAccess: IUserAccess): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userAccess);
    return this.http
      .put<IUserAccess>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserAccess>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserAccess[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userAccess: IUserAccess): IUserAccess {
    const copy: IUserAccess = Object.assign({}, userAccess, {
      lastModified: userAccess.lastModified && userAccess.lastModified.isValid() ? userAccess.lastModified.toJSON() : undefined,
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
      res.body.forEach((userAccess: IUserAccess) => {
        userAccess.lastModified = userAccess.lastModified ? moment(userAccess.lastModified) : undefined;
      });
    }
    return res;
  }
}
