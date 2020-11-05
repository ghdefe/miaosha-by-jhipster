import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoodOrder } from 'app/shared/model/good-order.model';

@Component({
  selector: 'jhi-good-order-detail',
  templateUrl: './good-order-detail.component.html',
})
export class GoodOrderDetailComponent implements OnInit {
  goodOrder: IGoodOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goodOrder }) => (this.goodOrder = goodOrder));
  }

  previousState(): void {
    window.history.back();
  }
}
