import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGoodOrder } from 'app/shared/model/good-order.model';
import { GoodOrderService } from './good-order.service';

@Component({
  templateUrl: './good-order-delete-dialog.component.html',
})
export class GoodOrderDeleteDialogComponent {
  goodOrder?: IGoodOrder;

  constructor(protected goodOrderService: GoodOrderService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.goodOrderService.delete(id).subscribe(() => {
      this.eventManager.broadcast('goodOrderListModification');
      this.activeModal.close();
    });
  }
}
