import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaluka, Taluka } from 'app/shared/model/taluka.model';
import { TalukaService } from './taluka.service';
import { TalukaComponent } from './taluka.component';
import { TalukaDetailComponent } from './taluka-detail.component';
import { TalukaUpdateComponent } from './taluka-update.component';

@Injectable({ providedIn: 'root' })
export class TalukaResolve implements Resolve<ITaluka> {
  constructor(private service: TalukaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaluka> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taluka: HttpResponse<Taluka>) => {
          if (taluka.body) {
            return of(taluka.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Taluka());
  }
}

export const talukaRoute: Routes = [
  {
    path: '',
    component: TalukaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Talukas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TalukaDetailComponent,
    resolve: {
      taluka: TalukaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Talukas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TalukaUpdateComponent,
    resolve: {
      taluka: TalukaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Talukas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TalukaUpdateComponent,
    resolve: {
      taluka: TalukaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Talukas',
    },
    canActivate: [UserRouteAccessService],
  },
];
