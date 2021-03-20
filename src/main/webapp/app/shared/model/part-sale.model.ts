import { Moment } from 'moment';
import { IPriceList } from 'app/shared/model/price-list.model';

export interface IPartSale {
  id?: number;
  name?: string;
  price?: number;
  createdBy?: string;
  createdAt?: Moment;
  lastModifiedBy?: string;
  lastUpdatedAt?: Moment;
  priceList?: IPriceList;
}

export class PartSale implements IPartSale {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public createdBy?: string,
    public createdAt?: Moment,
    public lastModifiedBy?: string,
    public lastUpdatedAt?: Moment,
    public priceList?: IPriceList
  ) {}
}
