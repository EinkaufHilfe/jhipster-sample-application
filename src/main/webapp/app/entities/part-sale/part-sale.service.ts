import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartSale } from 'app/shared/model/part-sale.model';

type EntityResponseType = HttpResponse<IPartSale>;
type EntityArrayResponseType = HttpResponse<IPartSale[]>;

@Injectable({ providedIn: 'root' })
export class PartSaleService {
  public resourceUrl = SERVER_API_URL + 'api/part-sales';

  constructor(protected http: HttpClient) {}

  create(partSale: IPartSale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partSale);
    return this.http
      .post<IPartSale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partSale: IPartSale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partSale);
    return this.http
      .put<IPartSale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartSale>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartSale[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(partSale: IPartSale): IPartSale {
    const copy: IPartSale = Object.assign({}, partSale, {
      createdAt: partSale.createdAt && partSale.createdAt.isValid() ? partSale.createdAt.toJSON() : undefined,
      lastUpdatedAt: partSale.lastUpdatedAt && partSale.lastUpdatedAt.isValid() ? partSale.lastUpdatedAt.toJSON() : undefined,
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
      res.body.forEach((partSale: IPartSale) => {
        partSale.createdAt = partSale.createdAt ? moment(partSale.createdAt) : undefined;
        partSale.lastUpdatedAt = partSale.lastUpdatedAt ? moment(partSale.lastUpdatedAt) : undefined;
      });
    }
    return res;
  }
}
