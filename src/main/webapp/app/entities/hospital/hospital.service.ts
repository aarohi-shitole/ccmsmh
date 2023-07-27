import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHospital } from 'app/shared/model/hospital.model';

type EntityResponseType = HttpResponse<IHospital>;
type EntityArrayResponseType = HttpResponse<IHospital[]>;

@Injectable({ providedIn: 'root' })
export class HospitalService {
  public resourceUrl = SERVER_API_URL + 'api/hospitals';

  constructor(protected http: HttpClient) {}

  create(hospital: IHospital): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hospital);
    return this.http
      .post<IHospital>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(hospital: IHospital): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hospital);
    return this.http
      .put<IHospital>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHospital>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHospital[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(hospital: IHospital): IHospital {
    const copy: IHospital = Object.assign({}, hospital, {
      lastModified: hospital.lastModified && hospital.lastModified.isValid() ? hospital.lastModified.toJSON() : undefined,
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
      res.body.forEach((hospital: IHospital) => {
        hospital.lastModified = hospital.lastModified ? moment(hospital.lastModified) : undefined;
      });
    }
    return res;
  }
}
