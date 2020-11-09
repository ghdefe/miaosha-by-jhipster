import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IGoodOrder, GoodOrder } from 'app/shared/model/good-order.model';
import { GoodOrderService } from './good-order.service';

@Component({
  selector: 'jhi-good-order-update',
  templateUrl: './good-order-update.component.html',
})
export class GoodOrderUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    goodId: [null, [Validators.required]],
    buyerId: [null, [Validators.required]],
    price: [null, [Validators.required, Validators.min(0)]],
    activityId: [],
    isPayed: [],
    isDelivered: [],
    isRefund: [],
    createTime: [],
  });

  constructor(protected goodOrderService: GoodOrderService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goodOrder }) => {
      if (!goodOrder.id) {
        const today = moment().startOf('day');
        goodOrder.createTime = today;
      }

      this.updateForm(goodOrder);
    });
  }

  updateForm(goodOrder: IGoodOrder): void {
    this.editForm.patchValue({
      id: goodOrder.id,
      goodId: goodOrder.goodId,
      buyerId: goodOrder.buyerId,
      price: goodOrder.price,
      activityId: goodOrder.activityId,
      isPayed: goodOrder.isPayed,
      isDelivered: goodOrder.isDelivered,
      isRefund: goodOrder.isRefund,
      createTime: goodOrder.createTime ? goodOrder.createTime.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const goodOrder = this.createFromForm();
    if (goodOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.goodOrderService.update(goodOrder));
    } else {
      this.subscribeToSaveResponse(this.goodOrderService.create(goodOrder));
    }
  }

  private createFromForm(): IGoodOrder {
    return {
      ...new GoodOrder(),
      id: this.editForm.get(['id'])!.value,
      goodId: this.editForm.get(['goodId'])!.value,
      buyerId: this.editForm.get(['buyerId'])!.value,
      price: this.editForm.get(['price'])!.value,
      activityId: this.editForm.get(['activityId'])!.value,
      isPayed: this.editForm.get(['isPayed'])!.value,
      isDelivered: this.editForm.get(['isDelivered'])!.value,
      isRefund: this.editForm.get(['isRefund'])!.value,
      createTime: this.editForm.get(['createTime'])!.value ? moment(this.editForm.get(['createTime'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoodOrder>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
