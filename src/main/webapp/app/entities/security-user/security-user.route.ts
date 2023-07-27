import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISecurityUser, SecurityUser } from 'app/shared/model/security-user.model';
import { SecurityUserService } from './security-user.service';
import { SecurityUserComponent } from './security-user.component';
import { SecurityUserDetailComponent } from './security-user-detail.component';
import { SecurityUserUpdateComponent } from './security-user-update.component';

@Injectable({ providedIn: 'root' })
export class SecurityUserResolve implements Resolve<ISecurityUser> {
  constructor(private service: SecurityUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISecurityUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((securityUser: HttpResponse<SecurityUser>) => {
          if (securityUser.body) {
            return of(securityUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SecurityUser());
  }
}

export const securityUserRoute: Routes = [
  {
    path: '',
    component: SecurityUserComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'SecurityUsers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SecurityUserDetailComponent,
    resolve: {
      securityUser: SecurityUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SecurityUsers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SecurityUserUpdateComponent,
    resolve: {
      securityUser: SecurityUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SecurityUsers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SecurityUserUpdateComponent,
    resolve: {
      securityUser: SecurityUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SecurityUsers',
    },
    canActivate: [UserRouteAccessService],
  },
];
