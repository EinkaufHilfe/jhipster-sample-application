import { Moment } from 'moment';
import { IPriceList } from 'app/shared/model/price-list.model';

export interface IConditionDefinition {
  id?: number;
  definition?: string;
  description?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastUpdatedAt?: Moment;
  priceList?: IPriceList;
}

export class ConditionDefinition implements IConditionDefinition {
  constructor(
    public id?: number,
    public definition?: string,
    public description?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastUpdatedAt?: Moment,
    public priceList?: IPriceList
  ) {}
}
