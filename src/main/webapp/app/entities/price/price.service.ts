import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPrice } from 'app/shared/model/price.model';

type EntityResponseType = HttpResponse<IPrice>;
type EntityArrayResponseType = HttpResponse<IPrice[]>;

@Injectable({ providedIn: 'root' })
export class PriceService {
  public resourceUrl = SERVER_API_URL + 'api/prices';

  constructor(protected http: HttpClient) {}

  create(price: IPrice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(price);
    return this.http
      .post<IPrice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(price: IPrice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(price);
    return this.http
      .put<IPrice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPrice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPrice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(price: IPrice): IPrice {
    const copy: IPrice = Object.assign({}, price, {
      createdDate: price.createdDate && price.createdDate.isValid() ? price.createdDate.toJSON() : undefined,
      lastUpdatedAt: price.lastUpdatedAt && price.lastUpdatedAt.isValid() ? price.lastUpdatedAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastUpdatedAt = res.body.lastUpdatedAt ? moment(res.body.lastUpdatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((price: IPrice) => {
        price.createdDate = price.createdDate ? moment(price.createdDate) : undefined;
        price.lastUpdatedAt = price.lastUpdatedAt ? moment(price.lastUpdatedAt) : undefined;
      });
    }
    return res;
  }
}
