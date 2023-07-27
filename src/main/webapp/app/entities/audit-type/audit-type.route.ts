import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAuditType, AuditType } from 'app/shared/model/audit-type.model';
import { AuditTypeService } from './audit-type.service';
import { AuditTypeComponent } from './audit-type.component';
import { AuditTypeDetailComponent } from './audit-type-detail.component';
import { AuditTypeUpdateComponent } from './audit-type-update.component';

@Injectable({ providedIn: 'root' })
export class AuditTypeResolve implements Resolve<IAuditType> {
  constructor(private service: AuditTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuditType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((auditType: HttpResponse<AuditType>) => {
          if (auditType.body) {
            return of(auditType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AuditType());
  }
}

export const auditTypeRoute: Routes = [
  {
    path: '',
    component: AuditTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'AuditTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuditTypeDetailComponent,
    resolve: {
      auditType: AuditTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AuditTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuditTypeUpdateComponent,
    resolve: {
      auditType: AuditTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AuditTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuditTypeUpdateComponent,
    resolve: {
      auditType: AuditTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AuditTypes',
    },
    canActivate: [UserRouteAccessService],
  },
];
