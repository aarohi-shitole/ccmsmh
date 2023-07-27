import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITripDetails, TripDetails } from 'app/shared/model/trip-details.model';
import { TripDetailsService } from './trip-details.service';
import { TripDetailsComponent } from './trip-details.component';
import { TripDetailsDetailComponent } from './trip-details-detail.component';
import { TripDetailsUpdateComponent } from './trip-details-update.component';

@Injectable({ providedIn: 'root' })
export class TripDetailsResolve implements Resolve<ITripDetails> {
  constructor(private service: TripDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITripDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tripDetails: HttpResponse<TripDetails>) => {
          if (tripDetails.body) {
            return of(tripDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TripDetails());
  }
}

export const tripDetailsRoute: Routes = [
  {
    path: '',
    component: TripDetailsComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'TripDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TripDetailsDetailComponent,
    resolve: {
      tripDetails: TripDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TripDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TripDetailsUpdateComponent,
    resolve: {
      tripDetails: TripDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TripDetails',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TripDetailsUpdateComponent,
    resolve: {
      tripDetails: TripDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TripDetails',
    },
    canActivate: [UserRouteAccessService],
  },
];
