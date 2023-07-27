import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHospitalType, HospitalType } from 'app/shared/model/hospital-type.model';
import { HospitalTypeService } from './hospital-type.service';
import { HospitalTypeComponent } from './hospital-type.component';
import { HospitalTypeDetailComponent } from './hospital-type-detail.component';
import { HospitalTypeUpdateComponent } from './hospital-type-update.component';

@Injectable({ providedIn: 'root' })
export class HospitalTypeResolve implements Resolve<IHospitalType> {
  constructor(private service: HospitalTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHospitalType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((hospitalType: HttpResponse<HospitalType>) => {
          if (hospitalType.body) {
            return of(hospitalType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HospitalType());
  }
}

export const hospitalTypeRoute: Routes = [
  {
    path: '',
    component: HospitalTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'HospitalTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HospitalTypeDetailComponent,
    resolve: {
      hospitalType: HospitalTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'HospitalTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HospitalTypeUpdateComponent,
    resolve: {
      hospitalType: HospitalTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'HospitalTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HospitalTypeUpdateComponent,
    resolve: {
      hospitalType: HospitalTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'HospitalTypes',
    },
    canActivate: [UserRouteAccessService],
  },
];
