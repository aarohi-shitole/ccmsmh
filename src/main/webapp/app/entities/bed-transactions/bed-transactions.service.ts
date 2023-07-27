import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBedTransactions } from 'app/shared/model/bed-transactions.model';

type EntityResponseType = HttpResponse<IBedTransactions>;
type EntityArrayResponseType = HttpResponse<IBedTransactions[]>;

@Injectable({ providedIn: 'root' })
export class BedTransactionsService {
  public resourceUrl = SERVER_API_URL + 'api/bed-transactions';

  constructor(protected http: HttpClient) {}

  create(bedTransactions: IBedTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedTransactions);
    return this.http
      .post<IBedTransactions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bedTransactions: IBedTransactions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedTransactions);
    return this.http
      .put<IBedTransactions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBedTransactions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBedTransactions[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bedTransactions: IBedTransactions): IBedTransactions {
    const copy: IBedTransactions = Object.assign({}, bedTransactions, {
      lastModified:
        bedTransactions.lastModified && bedTransactions.lastModified.isValid() ? bedTransactions.lastModified.toJSON() : undefined,
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
      res.body.forEach((bedTransactions: IBedTransactions) => {
        bedTransactions.lastModified = bedTransactions.lastModified ? moment(bedTransactions.lastModified) : undefined;
      });
    }
    return res;
  }
}
