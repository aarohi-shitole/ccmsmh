import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBedType } from 'app/shared/model/bed-type.model';

type EntityResponseType = HttpResponse<IBedType>;
type EntityArrayResponseType = HttpResponse<IBedType[]>;

@Injectable({ providedIn: 'root' })
export class BedTypeService {
  public resourceUrl = SERVER_API_URL + 'api/bed-types';

  constructor(protected http: HttpClient) {}

  create(bedType: IBedType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedType);
    return this.http
      .post<IBedType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bedType: IBedType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedType);
    return this.http
      .put<IBedType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBedType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBedType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bedType: IBedType): IBedType {
    const copy: IBedType = Object.assign({}, bedType, {
      lastModified: bedType.lastModified && bedType.lastModified.isValid() ? bedType.lastModified.toJSON() : undefined,
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
      res.body.forEach((bedType: IBedType) => {
        bedType.lastModified = bedType.lastModified ? moment(bedType.lastModified) : undefined;
      });
    }
    return res;
  }
}
