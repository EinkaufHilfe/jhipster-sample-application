import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPriceList } from 'app/shared/model/price-list.model';

type EntityResponseType = HttpResponse<IPriceList>;
type EntityArrayResponseType = HttpResponse<IPriceList[]>;

@Injectable({ providedIn: 'root' })
export class PriceListService {
  public resourceUrl = SERVER_API_URL + 'api/price-lists';

  constructor(protected http: HttpClient) {}

  create(priceList: IPriceList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(priceList);
    return this.http
      .post<IPriceList>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(priceList: IPriceList): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(priceList);
    return this.http
      .put<IPriceList>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPriceList>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPriceList[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(priceList: IPriceList): IPriceList {
    const copy: IPriceList = Object.assign({}, priceList, {
      validFrom: priceList.validFrom && priceList.validFrom.isValid() ? priceList.validFrom.format(DATE_FORMAT) : undefined,
      validTill: priceList.validTill && priceList.validTill.isValid() ? priceList.validTill.format(DATE_FORMAT) : undefined,
      createdAt: priceList.createdAt && priceList.createdAt.isValid() ? priceList.createdAt.toJSON() : undefined,
      lastUpdatedAt: priceList.lastUpdatedAt && priceList.lastUpdatedAt.isValid() ? priceList.lastUpdatedAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.validFrom = res.body.validFrom ? moment(res.body.validFrom) : undefined;
      res.body.validTill = res.body.validTill ? moment(res.body.validTill) : undefined;
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.lastUpdatedAt = res.body.lastUpdatedAt ? moment(res.body.lastUpdatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((priceList: IPriceList) => {
        priceList.validFrom = priceList.validFrom ? moment(priceList.validFrom) : undefined;
        priceList.validTill = priceList.validTill ? moment(priceList.validTill) : undefined;
        priceList.createdAt = priceList.createdAt ? moment(priceList.createdAt) : undefined;
        priceList.lastUpdatedAt = priceList.lastUpdatedAt ? moment(priceList.lastUpdatedAt) : undefined;
      });
    }
    return res;
  }
}
