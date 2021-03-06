import { Moment } from 'moment';
import { IPriceList } from 'app/shared/model/price-list.model';

export interface ICustomer {
  id?: number;
  name?: string;
  createdBy?: string;
  createdAt?: Moment;
  lastUpdatedBy?: string;
  lastUpdatedAt?: Moment;
  priceLists?: IPriceList[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public createdBy?: string,
    public createdAt?: Moment,
    public lastUpdatedBy?: string,
    public lastUpdatedAt?: Moment,
    public priceLists?: IPriceList[]
  ) {}
}
