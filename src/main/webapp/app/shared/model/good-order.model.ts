import { Moment } from 'moment';

export interface IGoodOrder {
  id?: number;
  goodId?: number;
  buyerId?: number;
  price?: number;
  activityId?: number;
  isPayed?: boolean;
  isDelivered?: boolean;
  isRefund?: boolean;
  createTime?: Moment;
}

export class GoodOrder implements IGoodOrder {
  constructor(
    public id?: number,
    public goodId?: number,
    public buyerId?: number,
    public price?: number,
    public activityId?: number,
    public isPayed?: boolean,
    public isDelivered?: boolean,
    public isRefund?: boolean,
    public createTime?: Moment
  ) {
    this.isPayed = this.isPayed || false;
    this.isDelivered = this.isDelivered || false;
    this.isRefund = this.isRefund || false;
  }
}
