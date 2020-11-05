import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGoodOrder } from 'app/shared/model/good-order.model';
import { GoodOrderService } from './good-order.service';
import { GoodOrderDeleteDialogComponent } from './good-order-delete-dialog.component';

@Component({
  selector: 'jhi-good-order',
  templateUrl: './good-order.component.html',
})
export class GoodOrderComponent implements OnInit, OnDestroy {
  goodOrders?: IGoodOrder[];
  eventSubscriber?: Subscription;

  constructor(protected goodOrderService: GoodOrderService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.goodOrderService.query().subscribe((res: HttpResponse<IGoodOrder[]>) => (this.goodOrders = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGoodOrders();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGoodOrder): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGoodOrders(): void {
    this.eventSubscriber = this.eventManager.subscribe('goodOrderListModification', () => this.loadAll());
  }

  delete(goodOrder: IGoodOrder): void {
    const modalRef = this.modalService.open(GoodOrderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.goodOrder = goodOrder;
  }
}
