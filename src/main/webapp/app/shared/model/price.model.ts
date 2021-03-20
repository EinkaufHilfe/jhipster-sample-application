import { Moment } from 'moment';
import { IPriceList } from 'app/shared/model/price-list.model';

export interface IPrice {
  id?: number;
  model?: string;
  condition?: string;
  price?: number;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  priceList?: IPriceList;
}

export class Price implements IPrice {
  constructor(
    public id?: number,
    public model?: string,
    public condition?: string,
    public price?: number,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public priceList?: IPriceList
  ) {}
}
