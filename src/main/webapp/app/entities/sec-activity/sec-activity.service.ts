import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISecActivity } from 'app/shared/model/sec-activity.model';

type EntityResponseType = HttpResponse<ISecActivity>;
type EntityArrayResponseType = HttpResponse<ISecActivity[]>;

@Injectable({ providedIn: 'root' })
export class SecActivityService {
  public resourceUrl = SERVER_API_URL + 'api/sec-activities';

  constructor(protected http: HttpClient) {}

  create(secActivity: ISecActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(secActivity);
    return this.http
      .post<ISecActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(secActivity: ISecActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(secActivity);
    return this.http
      .put<ISecActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISecActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISecActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(secActivity: ISecActivity): ISecActivity {
    const copy: ISecActivity = Object.assign({}, secActivity, {
      start: secActivity.start && secActivity.start.isValid() ? secActivity.start.toJSON() : undefined,
      end: secActivity.end && secActivity.end.isValid() ? secActivity.end.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.start = res.body.start ? moment(res.body.start) : undefined;
      res.body.end = res.body.end ? moment(res.body.end) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((secActivity: ISecActivity) => {
        secActivity.start = secActivity.start ? moment(secActivity.start) : undefined;
        secActivity.end = secActivity.end ? moment(secActivity.end) : undefined;
      });
    }
    return res;
  }
}
