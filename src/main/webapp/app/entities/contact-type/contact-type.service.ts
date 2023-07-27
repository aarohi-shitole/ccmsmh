import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContactType } from 'app/shared/model/contact-type.model';

type EntityResponseType = HttpResponse<IContactType>;
type EntityArrayResponseType = HttpResponse<IContactType[]>;

@Injectable({ providedIn: 'root' })
export class ContactTypeService {
  public resourceUrl = SERVER_API_URL + 'api/contact-types';

  constructor(protected http: HttpClient) {}

  create(contactType: IContactType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contactType);
    return this.http
      .post<IContactType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contactType: IContactType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contactType);
    return this.http
      .put<IContactType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContactType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContactType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contactType: IContactType): IContactType {
    const copy: IContactType = Object.assign({}, contactType, {
      lastModified: contactType.lastModified && contactType.lastModified.isValid() ? contactType.lastModified.toJSON() : undefined,
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
      res.body.forEach((contactType: IContactType) => {
        contactType.lastModified = contactType.lastModified ? moment(contactType.lastModified) : undefined;
      });
    }
    return res;
  }
}
