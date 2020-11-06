export interface IGood {
  id?: number;
  name?: string;
  imgUrl?: string;
  detail?: any;
  price?: number;
  sellerId?: number;
  stock?: number;
}

export class Good implements IGood {
  constructor(
    public id?: number,
    public name?: string,
    public imgUrl?: string,
    public detail?: any,
    public price?: number,
    public sellerId?: number,
    public stock?: number
  ) {}
}
