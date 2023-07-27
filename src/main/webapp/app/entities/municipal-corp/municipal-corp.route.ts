import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMunicipalCorp, MunicipalCorp } from 'app/shared/model/municipal-corp.model';
import { MunicipalCorpService } from './municipal-corp.service';
import { MunicipalCorpComponent } from './municipal-corp.component';
import { MunicipalCorpDetailComponent } from './municipal-corp-detail.component';
import { MunicipalCorpUpdateComponent } from './municipal-corp-update.component';

@Injectable({ providedIn: 'root' })
export class MunicipalCorpResolve implements Resolve<IMunicipalCorp> {
  constructor(private service: MunicipalCorpService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMunicipalCorp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((municipalCorp: HttpResponse<MunicipalCorp>) => {
          if (municipalCorp.body) {
            return of(municipalCorp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MunicipalCorp());
  }
}

export const municipalCorpRoute: Routes = [
  {
    path: '',
    component: MunicipalCorpComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'MunicipalCorps',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MunicipalCorpDetailComponent,
    resolve: {
      municipalCorp: MunicipalCorpResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MunicipalCorps',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MunicipalCorpUpdateComponent,
    resolve: {
      municipalCorp: MunicipalCorpResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MunicipalCorps',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MunicipalCorpUpdateComponent,
    resolve: {
      municipalCorp: MunicipalCorpResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MunicipalCorps',
    },
    canActivate: [UserRouteAccessService],
  },
];
