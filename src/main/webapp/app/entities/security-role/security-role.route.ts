import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISecurityRole, SecurityRole } from 'app/shared/model/security-role.model';
import { SecurityRoleService } from './security-role.service';
import { SecurityRoleComponent } from './security-role.component';
import { SecurityRoleDetailComponent } from './security-role-detail.component';
import { SecurityRoleUpdateComponent } from './security-role-update.component';

@Injectable({ providedIn: 'root' })
export class SecurityRoleResolve implements Resolve<ISecurityRole> {
  constructor(private service: SecurityRoleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISecurityRole> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((securityRole: HttpResponse<SecurityRole>) => {
          if (securityRole.body) {
            return of(securityRole.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SecurityRole());
  }
}

export const securityRoleRoute: Routes = [
  {
    path: '',
    component: SecurityRoleComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'SecurityRoles',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SecurityRoleDetailComponent,
    resolve: {
      securityRole: SecurityRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SecurityRoles',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SecurityRoleUpdateComponent,
    resolve: {
      securityRole: SecurityRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SecurityRoles',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SecurityRoleUpdateComponent,
    resolve: {
      securityRole: SecurityRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SecurityRoles',
    },
    canActivate: [UserRouteAccessService],
  },
];
