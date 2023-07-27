import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserAccess, UserAccess } from 'app/shared/model/user-access.model';
import { UserAccessService } from './user-access.service';
import { UserAccessComponent } from './user-access.component';
import { UserAccessDetailComponent } from './user-access-detail.component';
import { UserAccessUpdateComponent } from './user-access-update.component';

@Injectable({ providedIn: 'root' })
export class UserAccessResolve implements Resolve<IUserAccess> {
  constructor(private service: UserAccessService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserAccess> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userAccess: HttpResponse<UserAccess>) => {
          if (userAccess.body) {
            return of(userAccess.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserAccess());
  }
}

export const userAccessRoute: Routes = [
  {
    path: '',
    component: UserAccessComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'UserAccesses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserAccessDetailComponent,
    resolve: {
      userAccess: UserAccessResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserAccesses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserAccessUpdateComponent,
    resolve: {
      userAccess: UserAccessResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserAccesses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserAccessUpdateComponent,
    resolve: {
      userAccess: UserAccessResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserAccesses',
    },
    canActivate: [UserRouteAccessService],
  },
];
