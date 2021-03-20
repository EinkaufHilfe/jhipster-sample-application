import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConditionDefinition } from 'app/shared/model/condition-definition.model';

type EntityResponseType = HttpResponse<IConditionDefinition>;
type EntityArrayResponseType = HttpResponse<IConditionDefinition[]>;

@Injectable({ providedIn: 'root' })
export class ConditionDefinitionService {
  public resourceUrl = SERVER_API_URL + 'api/condition-definitions';

  constructor(protected http: HttpClient) {}

  create(conditionDefinition: IConditionDefinition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conditionDefinition);
    return this.http
      .post<IConditionDefinition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(conditionDefinition: IConditionDefinition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(conditionDefinition);
    return this.http
      .put<IConditionDefinition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConditionDefinition>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConditionDefinition[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(conditionDefinition: IConditionDefinition): IConditionDefinition {
    const copy: IConditionDefinition = Object.assign({}, conditionDefinition, {
      createdDate:
        conditionDefinition.createdDate && conditionDefinition.createdDate.isValid() ? conditionDefinition.createdDate.toJSON() : undefined,
      lastUpdatedAt:
        conditionDefinition.lastUpdatedAt && conditionDefinition.lastUpdatedAt.isValid()
          ? conditionDefinition.lastUpdatedAt.toJSON()
          : undefined,
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
      res.body.forEach((conditionDefinition: IConditionDefinition) => {
        conditionDefinition.createdDate = conditionDefinition.createdDate ? moment(conditionDefinition.createdDate) : undefined;
        conditionDefinition.lastUpdatedAt = conditionDefinition.lastUpdatedAt ? moment(conditionDefinition.lastUpdatedAt) : undefined;
      });
    }
    return res;
  }
}
