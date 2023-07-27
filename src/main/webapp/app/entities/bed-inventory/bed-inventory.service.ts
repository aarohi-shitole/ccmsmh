import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBedInventory } from 'app/shared/model/bed-inventory.model';

type EntityResponseType = HttpResponse<IBedInventory>;
type EntityArrayResponseType = HttpResponse<IBedInventory[]>;

@Injectable({ providedIn: 'root' })
export class BedInventoryService {
  public resourceUrl = SERVER_API_URL + 'api/bed-inventories';

  constructor(protected http: HttpClient) {}

  create(bedInventory: IBedInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedInventory);
    return this.http
      .post<IBedInventory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bedInventory: IBedInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bedInventory);
    return this.http
      .put<IBedInventory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBedInventory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBedInventory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bedInventory: IBedInventory): IBedInventory {
    const copy: IBedInventory = Object.assign({}, bedInventory, {
      lastModified: bedInventory.lastModified && bedInventory.lastModified.isValid() ? bedInventory.lastModified.toJSON() : undefined,
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
      res.body.forEach((bedInventory: IBedInventory) => {
        bedInventory.lastModified = bedInventory.lastModified ? moment(bedInventory.lastModified) : undefined;
      });
    }
    return res;
  }
}
