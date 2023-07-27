import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IState, State } from 'app/shared/model/state.model';
import { StateService } from './state.service';
import { StateComponent } from './state.component';
import { StateDetailComponent } from './state-detail.component';
import { StateUpdateComponent } from './state-update.component';

@Injectable({ providedIn: 'root' })
export class StateResolve implements Resolve<IState> {
  constructor(private service: StateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IState> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((state: HttpResponse<State>) => {
          if (state.body) {
            return of(state.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new State());
  }
}

export const stateRoute: Routes = [
  {
    path: '',
    component: StateComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'States',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StateDetailComponent,
    resolve: {
      state: StateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'States',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StateUpdateComponent,
    resolve: {
      state: StateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'States',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StateUpdateComponent,
    resolve: {
      state: StateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'States',
    },
    canActivate: [UserRouteAccessService],
  },
];
