import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGoodOrder } from 'app/shared/model/good-order.model';

type EntityResponseType = HttpResponse<IGoodOrder>;
type EntityArrayResponseType = HttpResponse<IGoodOrder[]>;

@Injectable({ providedIn: 'root' })
export class GoodOrderService {
  public resourceUrl = SERVER_API_URL + 'api/good-orders';

  constructor(protected http: HttpClient) {}

  create(goodOrder: IGoodOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(goodOrder);
    return this.http
      .post<IGoodOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(goodOrder: IGoodOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(goodOrder);
    return this.http
      .put<IGoodOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGoodOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGoodOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(goodOrder: IGoodOrder): IGoodOrder {
    const copy: IGoodOrder = Object.assign({}, goodOrder, {
      createTime: goodOrder.createTime && goodOrder.createTime.isValid() ? goodOrder.createTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createTime = res.body.createTime ? moment(res.body.createTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((goodOrder: IGoodOrder) => {
        goodOrder.createTime = goodOrder.createTime ? moment(goodOrder.createTime) : undefined;
      });
    }
    return res;
  }
}
