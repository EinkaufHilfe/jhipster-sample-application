import { Moment } from 'moment';
import { IPriceList } from 'app/shared/model/price-list.model';

export interface IDeduction {
  id?: number;
  reason?: string;
  price?: number;
  createdBy?: string;
  createdAt?: Moment;
  lastUpdatedBy?: string;
  lastUpdatedAt?: Moment;
  priceList?: IPriceList;
}

export class Deduction implements IDeduction {
  constructor(
    public id?: number,
    public reason?: string,
    public price?: number,
    public createdBy?: string,
    public createdAt?: Moment,
    public lastUpdatedBy?: string,
    public lastUpdatedAt?: Moment,
    public priceList?: IPriceList
  ) {}
}
