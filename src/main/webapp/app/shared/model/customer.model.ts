import { Moment } from 'moment';
import { IPriceList } from 'app/shared/model/price-list.model';

export interface ICustomer {
  id?: number;
  name?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  priceLists?: IPriceList[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public priceLists?: IPriceList[]
  ) {}
}

export interface INewCustomer {
  name?: string;
}

export class NewCustomer implements INewCustomer {
  constructor(public name?: string) {}
}
