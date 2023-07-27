import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventoryMaster } from 'app/shared/model/inventory-master.model';

type EntityResponseType = HttpResponse<IInventoryMaster>;
type EntityArrayResponseType = HttpResponse<IInventoryMaster[]>;

@Injectable({ providedIn: 'root' })
export class InventoryMasterService {
  public resourceUrl = SERVER_API_URL + 'api/inventory-masters';

  constructor(protected http: HttpClient) {}

  create(inventoryMaster: IInventoryMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryMaster);
    return this.http
      .post<IInventoryMaster>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventoryMaster: IInventoryMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryMaster);
    return this.http
      .put<IInventoryMaster>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventoryMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventoryMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inventoryMaster: IInventoryMaster): IInventoryMaster {
    const copy: IInventoryMaster = Object.assign({}, inventoryMaster, {
      lastModified:
        inventoryMaster.lastModified && inventoryMaster.lastModified.isValid() ? inventoryMaster.lastModified.toJSON() : undefined,
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
      res.body.forEach((inventoryMaster: IInventoryMaster) => {
        inventoryMaster.lastModified = inventoryMaster.lastModified ? moment(inventoryMaster.lastModified) : undefined;
      });
    }
    return res;
  }
}
