import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISecActivity, SecActivity } from 'app/shared/model/sec-activity.model';
import { SecActivityService } from './sec-activity.service';
import { SecActivityComponent } from './sec-activity.component';
import { SecActivityDetailComponent } from './sec-activity-detail.component';
import { SecActivityUpdateComponent } from './sec-activity-update.component';

@Injectable({ providedIn: 'root' })
export class SecActivityResolve implements Resolve<ISecActivity> {
  constructor(private service: SecActivityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISecActivity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((secActivity: HttpResponse<SecActivity>) => {
          if (secActivity.body) {
            return of(secActivity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SecActivity());
  }
}

export const secActivityRoute: Routes = [
  {
    path: '',
    component: SecActivityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'miaoshaByJhipsterApp.secActivity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SecActivityDetailComponent,
    resolve: {
      secActivity: SecActivityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'miaoshaByJhipsterApp.secActivity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SecActivityUpdateComponent,
    resolve: {
      secActivity: SecActivityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'miaoshaByJhipsterApp.secActivity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SecActivityUpdateComponent,
    resolve: {
      secActivity: SecActivityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'miaoshaByJhipsterApp.secActivity.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
