import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'good',
        loadChildren: () => import('./good/good.module').then(m => m.MiaoshaByJhipsterGoodModule),
      },
      {
        path: 'sec-activity',
        loadChildren: () => import('./sec-activity/sec-activity.module').then(m => m.MiaoshaByJhipsterSecActivityModule),
      },
      {
        path: 'good-order',
        loadChildren: () => import('./good-order/good-order.module').then(m => m.MiaoshaByJhipsterGoodOrderModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MiaoshaByJhipsterEntityModule {}
