import { Moment } from 'moment';

export interface ISecActivity {
  id?: number;
  name?: string;
  goodId?: number;
  author?: number;
  secPrice?: number;
  start?: Moment;
  end?: Moment;
}

export class SecActivity implements ISecActivity {
  constructor(
    public id?: number,
    public name?: string,
    public goodId?: number,
    public author?: number,
    public secPrice?: number,
    public start?: Moment,
    public end?: Moment
  ) {}
}
