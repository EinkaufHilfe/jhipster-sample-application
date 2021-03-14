import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConditionDefinition, ConditionDefinition } from 'app/shared/model/condition-definition.model';
import { ConditionDefinitionService } from './condition-definition.service';
import { ConditionDefinitionComponent } from './condition-definition.component';
import { ConditionDefinitionDetailComponent } from './condition-definition-detail.component';
import { ConditionDefinitionUpdateComponent } from './condition-definition-update.component';

@Injectable({ providedIn: 'root' })
export class ConditionDefinitionResolve implements Resolve<IConditionDefinition> {
  constructor(private service: ConditionDefinitionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConditionDefinition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((conditionDefinition: HttpResponse<ConditionDefinition>) => {
          if (conditionDefinition.body) {
            return of(conditionDefinition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConditionDefinition());
  }
}

export const conditionDefinitionRoute: Routes = [
  {
    path: '',
    component: ConditionDefinitionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'application.conditionDefinition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConditionDefinitionDetailComponent,
    resolve: {
      conditionDefinition: ConditionDefinitionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'application.conditionDefinition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConditionDefinitionUpdateComponent,
    resolve: {
      conditionDefinition: ConditionDefinitionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'application.conditionDefinition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConditionDefinitionUpdateComponent,
    resolve: {
      conditionDefinition: ConditionDefinitionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'application.conditionDefinition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
