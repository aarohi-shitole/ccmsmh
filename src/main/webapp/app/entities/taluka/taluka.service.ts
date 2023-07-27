import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaluka } from 'app/shared/model/taluka.model';

type EntityResponseType = HttpResponse<ITaluka>;
type EntityArrayResponseType = HttpResponse<ITaluka[]>;

@Injectable({ providedIn: 'root' })
export class TalukaService {
  public resourceUrl = SERVER_API_URL + 'api/talukas';

  constructor(protected http: HttpClient) {}

  create(taluka: ITaluka): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taluka);
    return this.http
      .post<ITaluka>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taluka: ITaluka): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taluka);
    return this.http
      .put<ITaluka>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaluka>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaluka[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(taluka: ITaluka): ITaluka {
    const copy: ITaluka = Object.assign({}, taluka, {
      lastModified: taluka.lastModified && taluka.lastModified.isValid() ? taluka.lastModified.toJSON() : undefined,
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
      res.body.forEach((taluka: ITaluka) => {
        taluka.lastModified = taluka.lastModified ? moment(taluka.lastModified) : undefined;
      });
    }
    return res;
  }
}
