import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartSale, PartSale } from 'app/shared/model/part-sale.model';
import { PartSaleService } from './part-sale.service';
import { PartSaleComponent } from './part-sale.component';
import { PartSaleDetailComponent } from './part-sale-detail.component';
import { PartSaleUpdateComponent } from './part-sale-update.component';

@Injectable({ providedIn: 'root' })
export class PartSaleResolve implements Resolve<IPartSale> {
  constructor(private service: PartSaleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartSale> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partSale: HttpResponse<PartSale>) => {
          if (partSale.body) {
            return of(partSale.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartSale());
  }
}

export const partSaleRoute: Routes = [
  {
    path: '',
    component: PartSaleComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partSale.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartSaleDetailComponent,
    resolve: {
      partSale: PartSaleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partSale.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartSaleUpdateComponent,
    resolve: {
      partSale: PartSaleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partSale.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartSaleUpdateComponent,
    resolve: {
      partSale: PartSaleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partSale.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
