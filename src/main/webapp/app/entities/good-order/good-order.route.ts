import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGoodOrder, GoodOrder } from 'app/shared/model/good-order.model';
import { GoodOrderService } from './good-order.service';
import { GoodOrderComponent } from './good-order.component';
import { GoodOrderDetailComponent } from './good-order-detail.component';
import { GoodOrderUpdateComponent } from './good-order-update.component';

@Injectable({ providedIn: 'root' })
export class GoodOrderResolve implements Resolve<IGoodOrder> {
  constructor(private service: GoodOrderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGoodOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((goodOrder: HttpResponse<GoodOrder>) => {
          if (goodOrder.body) {
            return of(goodOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GoodOrder());
  }
}

export const goodOrderRoute: Routes = [
  {
    path: '',
    component: GoodOrderComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'miaoshaByJhipsterApp.goodOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GoodOrderDetailComponent,
    resolve: {
      goodOrder: GoodOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'miaoshaByJhipsterApp.goodOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GoodOrderUpdateComponent,
    resolve: {
      goodOrder: GoodOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'miaoshaByJhipsterApp.goodOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GoodOrderUpdateComponent,
    resolve: {
      goodOrder: GoodOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'miaoshaByJhipsterApp.goodOrder.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
