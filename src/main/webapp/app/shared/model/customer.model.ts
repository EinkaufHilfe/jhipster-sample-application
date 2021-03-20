import { Moment } from 'moment';
import { IPriceList } from 'app/shared/model/price-list.model';

export interface ICustomer {
  id?: number;
  name?: string;
  createdBy?: string;
  createdAt?: Moment;
  lastModifiedBy?: string;
  lastUpdatedAt?: Moment;
  priceLists?: IPriceList[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public createdBy?: string,
    public createdAt?: Moment,
    public lastModifiedBy?: string,
    public lastUpdatedAt?: Moment,
    public priceLists?: IPriceList[]
  ) {}
}

export interface INewCustomer {
  name?: string;
}

export class NewCustomer implements INewCustomer {
  constructor(public name?: string) {}
}
