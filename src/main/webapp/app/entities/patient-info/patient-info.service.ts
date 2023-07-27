import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPatientInfo } from 'app/shared/model/patient-info.model';

type EntityResponseType = HttpResponse<IPatientInfo>;
type EntityArrayResponseType = HttpResponse<IPatientInfo[]>;

@Injectable({ providedIn: 'root' })
export class PatientInfoService {
  public resourceUrl = SERVER_API_URL + 'api/patient-infos';

  constructor(protected http: HttpClient) {}

  create(patientInfo: IPatientInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientInfo);
    return this.http
      .post<IPatientInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(patientInfo: IPatientInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(patientInfo);
    return this.http
      .put<IPatientInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPatientInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPatientInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(patientInfo: IPatientInfo): IPatientInfo {
    const copy: IPatientInfo = Object.assign({}, patientInfo, {
      dateOfSampleCollection:
        patientInfo.dateOfSampleCollection && patientInfo.dateOfSampleCollection.isValid()
          ? patientInfo.dateOfSampleCollection.toJSON()
          : undefined,
      dateOfSampleReceived:
        patientInfo.dateOfSampleReceived && patientInfo.dateOfSampleReceived.isValid()
          ? patientInfo.dateOfSampleReceived.toJSON()
          : undefined,
      hospitalizationDate:
        patientInfo.hospitalizationDate && patientInfo.hospitalizationDate.isValid() ? patientInfo.hospitalizationDate.toJSON() : undefined,
      dateOfSampleTested:
        patientInfo.dateOfSampleTested && patientInfo.dateOfSampleTested.isValid() ? patientInfo.dateOfSampleTested.toJSON() : undefined,
      entryDate: patientInfo.entryDate && patientInfo.entryDate.isValid() ? patientInfo.entryDate.toJSON() : undefined,
      confirmationDate:
        patientInfo.confirmationDate && patientInfo.confirmationDate.isValid() ? patientInfo.confirmationDate.toJSON() : undefined,
      editedOn: patientInfo.editedOn && patientInfo.editedOn.isValid() ? patientInfo.editedOn.toJSON() : undefined,
      ccmsPullDate: patientInfo.ccmsPullDate && patientInfo.ccmsPullDate.isValid() ? patientInfo.ccmsPullDate.toJSON() : undefined,
      lastModified: patientInfo.lastModified && patientInfo.lastModified.isValid() ? patientInfo.lastModified.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfSampleCollection = res.body.dateOfSampleCollection ? moment(res.body.dateOfSampleCollection) : undefined;
      res.body.dateOfSampleReceived = res.body.dateOfSampleReceived ? moment(res.body.dateOfSampleReceived) : undefined;
      res.body.hospitalizationDate = res.body.hospitalizationDate ? moment(res.body.hospitalizationDate) : undefined;
      res.body.dateOfSampleTested = res.body.dateOfSampleTested ? moment(res.body.dateOfSampleTested) : undefined;
      res.body.entryDate = res.body.entryDate ? moment(res.body.entryDate) : undefined;
      res.body.confirmationDate = res.body.confirmationDate ? moment(res.body.confirmationDate) : undefined;
      res.body.editedOn = res.body.editedOn ? moment(res.body.editedOn) : undefined;
      res.body.ccmsPullDate = res.body.ccmsPullDate ? moment(res.body.ccmsPullDate) : undefined;
      res.body.lastModified = res.body.lastModified ? moment(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((patientInfo: IPatientInfo) => {
        patientInfo.dateOfSampleCollection = patientInfo.dateOfSampleCollection ? moment(patientInfo.dateOfSampleCollection) : undefined;
        patientInfo.dateOfSampleReceived = patientInfo.dateOfSampleReceived ? moment(patientInfo.dateOfSampleReceived) : undefined;
        patientInfo.hospitalizationDate = patientInfo.hospitalizationDate ? moment(patientInfo.hospitalizationDate) : undefined;
        patientInfo.dateOfSampleTested = patientInfo.dateOfSampleTested ? moment(patientInfo.dateOfSampleTested) : undefined;
        patientInfo.entryDate = patientInfo.entryDate ? moment(patientInfo.entryDate) : undefined;
        patientInfo.confirmationDate = patientInfo.confirmationDate ? moment(patientInfo.confirmationDate) : undefined;
        patientInfo.editedOn = patientInfo.editedOn ? moment(patientInfo.editedOn) : undefined;
        patientInfo.ccmsPullDate = patientInfo.ccmsPullDate ? moment(patientInfo.ccmsPullDate) : undefined;
        patientInfo.lastModified = patientInfo.lastModified ? moment(patientInfo.lastModified) : undefined;
      });
    }
    return res;
  }
}
