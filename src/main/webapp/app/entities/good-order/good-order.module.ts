import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiaoshaByJhipsterSharedModule } from 'app/shared/shared.module';
import { GoodOrderComponent } from './good-order.component';
import { GoodOrderDetailComponent } from './good-order-detail.component';
import { GoodOrderUpdateComponent } from './good-order-update.component';
import { GoodOrderDeleteDialogComponent } from './good-order-delete-dialog.component';
import { goodOrderRoute } from './good-order.route';

@NgModule({
  imports: [MiaoshaByJhipsterSharedModule, RouterModule.forChild(goodOrderRoute)],
  declarations: [GoodOrderComponent, GoodOrderDetailComponent, GoodOrderUpdateComponent, GoodOrderDeleteDialogComponent],
  entryComponents: [GoodOrderDeleteDialogComponent],
})
export class MiaoshaByJhipsterGoodOrderModule {}
