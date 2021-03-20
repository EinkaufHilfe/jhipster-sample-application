import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPrice, Price } from 'app/shared/model/price.model';
import { PriceService } from './price.service';
import { PriceComponent } from './price.component';
import { PriceDetailComponent } from './price-detail.component';
import { PriceUpdateComponent } from './price-update.component';

@Injectable({ providedIn: 'root' })
export class PriceResolve implements Resolve<IPrice> {
  constructor(private service: PriceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrice> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((price: HttpResponse<Price>) => {
          if (price.body) {
            return of(price.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Price());
  }
}

export const priceRoute: Routes = [
  {
    path: '',
    component: PriceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myApp.price.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PriceDetailComponent,
    resolve: {
      price: PriceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myApp.price.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PriceUpdateComponent,
    resolve: {
      price: PriceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myApp.price.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PriceUpdateComponent,
    resolve: {
      price: PriceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'myApp.price.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
