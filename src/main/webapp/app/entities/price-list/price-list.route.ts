import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPriceList, PriceList } from 'app/shared/model/price-list.model';
import { PriceListService } from './price-list.service';
import { PriceListComponent } from './price-list.component';
import { PriceListDetailComponent } from './price-list-detail.component';
import { PriceListUpdateComponent } from './price-list-update.component';

@Injectable({ providedIn: 'root' })
export class PriceListResolve implements Resolve<IPriceList> {
  constructor(private service: PriceListService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPriceList> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((priceList: HttpResponse<PriceList>) => {
          if (priceList.body) {
            return of(priceList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PriceList());
  }
}

export const priceListRoute: Routes = [
  {
    path: '',
    component: PriceListComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.priceList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PriceListDetailComponent,
    resolve: {
      priceList: PriceListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.priceList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PriceListUpdateComponent,
    resolve: {
      priceList: PriceListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.priceList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PriceListUpdateComponent,
    resolve: {
      priceList: PriceListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.priceList.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
