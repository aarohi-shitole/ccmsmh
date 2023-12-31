import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITripDetails } from 'app/shared/model/trip-details.model';

type EntityResponseType = HttpResponse<ITripDetails>;
type EntityArrayResponseType = HttpResponse<ITripDetails[]>;

@Injectable({ providedIn: 'root' })
export class TripDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/trip-details';

  constructor(protected http: HttpClient) {}

  create(tripDetails: ITripDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tripDetails);
    return this.http
      .post<ITripDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tripDetails: ITripDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tripDetails);
    return this.http
      .put<ITripDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITripDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITripDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tripDetails: ITripDetails): ITripDetails {
    const copy: ITripDetails = Object.assign({}, tripDetails, {
      createdDate: tripDetails.createdDate && tripDetails.createdDate.isValid() ? tripDetails.createdDate.toJSON() : undefined,
      lastModified: tripDetails.lastModified && tripDetails.lastModified.isValid() ? tripDetails.lastModified.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModified = res.body.lastModified ? moment(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tripDetails: ITripDetails) => {
        tripDetails.createdDate = tripDetails.createdDate ? moment(tripDetails.createdDate) : undefined;
        tripDetails.lastModified = tripDetails.lastModified ? moment(tripDetails.lastModified) : undefined;
      });
    }
    return res;
  }
}
