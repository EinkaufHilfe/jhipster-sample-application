import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeduction } from 'app/shared/model/deduction.model';

type EntityResponseType = HttpResponse<IDeduction>;
type EntityArrayResponseType = HttpResponse<IDeduction[]>;

@Injectable({ providedIn: 'root' })
export class DeductionService {
  public resourceUrl = SERVER_API_URL + 'api/deductions';

  constructor(protected http: HttpClient) {}

  create(deduction: IDeduction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deduction);
    return this.http
      .post<IDeduction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deduction: IDeduction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deduction);
    return this.http
      .put<IDeduction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDeduction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeduction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(deduction: IDeduction): IDeduction {
    const copy: IDeduction = Object.assign({}, deduction, {
      createdAt: deduction.createdAt && deduction.createdAt.isValid() ? deduction.createdAt.toJSON() : undefined,
      lastUpdatedAt: deduction.lastUpdatedAt && deduction.lastUpdatedAt.isValid() ? deduction.lastUpdatedAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.lastUpdatedAt = res.body.lastUpdatedAt ? moment(res.body.lastUpdatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((deduction: IDeduction) => {
        deduction.createdAt = deduction.createdAt ? moment(deduction.createdAt) : undefined;
        deduction.lastUpdatedAt = deduction.lastUpdatedAt ? moment(deduction.lastUpdatedAt) : undefined;
      });
    }
    return res;
  }
}
