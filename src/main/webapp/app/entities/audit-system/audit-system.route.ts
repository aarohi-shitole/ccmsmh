import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAuditSystem, AuditSystem } from 'app/shared/model/audit-system.model';
import { AuditSystemService } from './audit-system.service';
import { AuditSystemComponent } from './audit-system.component';
import { AuditSystemDetailComponent } from './audit-system-detail.component';
import { AuditSystemUpdateComponent } from './audit-system-update.component';

@Injectable({ providedIn: 'root' })
export class AuditSystemResolve implements Resolve<IAuditSystem> {
  constructor(private service: AuditSystemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuditSystem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((auditSystem: HttpResponse<AuditSystem>) => {
          if (auditSystem.body) {
            return of(auditSystem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AuditSystem());
  }
}

export const auditSystemRoute: Routes = [
  {
    path: '',
    component: AuditSystemComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'AuditSystems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuditSystemDetailComponent,
    resolve: {
      auditSystem: AuditSystemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AuditSystems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuditSystemUpdateComponent,
    resolve: {
      auditSystem: AuditSystemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AuditSystems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuditSystemUpdateComponent,
    resolve: {
      auditSystem: AuditSystemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AuditSystems',
    },
    canActivate: [UserRouteAccessService],
  },
];
