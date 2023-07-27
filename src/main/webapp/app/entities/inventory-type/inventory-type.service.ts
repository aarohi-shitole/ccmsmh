import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventoryType } from 'app/shared/model/inventory-type.model';

type EntityResponseType = HttpResponse<IInventoryType>;
type EntityArrayResponseType = HttpResponse<IInventoryType[]>;

@Injectable({ providedIn: 'root' })
export class InventoryTypeService {
  public resourceUrl = SERVER_API_URL + 'api/inventory-types';

  constructor(protected http: HttpClient) {}

  create(inventoryType: IInventoryType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryType);
    return this.http
      .post<IInventoryType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventoryType: IInventoryType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryType);
    return this.http
      .put<IInventoryType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventoryType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventoryType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inventoryType: IInventoryType): IInventoryType {
    const copy: IInventoryType = Object.assign({}, inventoryType, {
      lastModified: inventoryType.lastModified && inventoryType.lastModified.isValid() ? inventoryType.lastModified.toJSON() : undefined,
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
      res.body.forEach((inventoryType: IInventoryType) => {
        inventoryType.lastModified = inventoryType.lastModified ? moment(inventoryType.lastModified) : undefined;
      });
    }
    return res;
  }
}
