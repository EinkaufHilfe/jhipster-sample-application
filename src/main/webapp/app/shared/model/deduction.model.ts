import { Moment } from 'moment';
import { IPriceList } from 'app/shared/model/price-list.model';

export interface IDeduction {
  id?: number;
  reason?: string;
  price?: number;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  priceList?: IPriceList;
}

export class Deduction implements IDeduction {
  constructor(
    public id?: number,
    public reason?: string,
    public price?: number,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public priceList?: IPriceList
  ) {}
}
