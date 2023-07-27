import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBedType, BedType } from 'app/shared/model/bed-type.model';
import { BedTypeService } from './bed-type.service';
import { BedTypeComponent } from './bed-type.component';
import { BedTypeDetailComponent } from './bed-type-detail.component';
import { BedTypeUpdateComponent } from './bed-type-update.component';

@Injectable({ providedIn: 'root' })
export class BedTypeResolve implements Resolve<IBedType> {
  constructor(private service: BedTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBedType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bedType: HttpResponse<BedType>) => {
          if (bedType.body) {
            return of(bedType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BedType());
  }
}

export const bedTypeRoute: Routes = [
  {
    path: '',
    component: BedTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'BedTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BedTypeDetailComponent,
    resolve: {
      bedType: BedTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BedTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BedTypeUpdateComponent,
    resolve: {
      bedType: BedTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BedTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BedTypeUpdateComponent,
    resolve: {
      bedType: BedTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BedTypes',
    },
    canActivate: [UserRouteAccessService],
  },
];
