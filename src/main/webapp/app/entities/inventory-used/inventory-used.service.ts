import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventoryUsed } from 'app/shared/model/inventory-used.model';

type EntityResponseType = HttpResponse<IInventoryUsed>;
type EntityArrayResponseType = HttpResponse<IInventoryUsed[]>;

@Injectable({ providedIn: 'root' })
export class InventoryUsedService {
  public resourceUrl = SERVER_API_URL + 'api/inventory-useds';

  constructor(protected http: HttpClient) {}

  create(inventoryUsed: IInventoryUsed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryUsed);
    return this.http
      .post<IInventoryUsed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventoryUsed: IInventoryUsed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryUsed);
    return this.http
      .put<IInventoryUsed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventoryUsed>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventoryUsed[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inventoryUsed: IInventoryUsed): IInventoryUsed {
    const copy: IInventoryUsed = Object.assign({}, inventoryUsed, {
      lastModified: inventoryUsed.lastModified && inventoryUsed.lastModified.isValid() ? inventoryUsed.lastModified.toJSON() : undefined,
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
      res.body.forEach((inventoryUsed: IInventoryUsed) => {
        inventoryUsed.lastModified = inventoryUsed.lastModified ? moment(inventoryUsed.lastModified) : undefined;
      });
    }
    return res;
  }
}
