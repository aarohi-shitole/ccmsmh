import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHospitalType } from 'app/shared/model/hospital-type.model';

type EntityResponseType = HttpResponse<IHospitalType>;
type EntityArrayResponseType = HttpResponse<IHospitalType[]>;

@Injectable({ providedIn: 'root' })
export class HospitalTypeService {
  public resourceUrl = SERVER_API_URL + 'api/hospital-types';

  constructor(protected http: HttpClient) {}

  create(hospitalType: IHospitalType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hospitalType);
    return this.http
      .post<IHospitalType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(hospitalType: IHospitalType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hospitalType);
    return this.http
      .put<IHospitalType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHospitalType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHospitalType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(hospitalType: IHospitalType): IHospitalType {
    const copy: IHospitalType = Object.assign({}, hospitalType, {
      lastModified: hospitalType.lastModified && hospitalType.lastModified.isValid() ? hospitalType.lastModified.toJSON() : undefined,
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
      res.body.forEach((hospitalType: IHospitalType) => {
        hospitalType.lastModified = hospitalType.lastModified ? moment(hospitalType.lastModified) : undefined;
      });
    }
    return res;
  }
}
