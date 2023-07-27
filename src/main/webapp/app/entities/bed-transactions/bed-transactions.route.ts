import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBedTransactions, BedTransactions } from 'app/shared/model/bed-transactions.model';
import { BedTransactionsService } from './bed-transactions.service';
import { BedTransactionsComponent } from './bed-transactions.component';
import { BedTransactionsDetailComponent } from './bed-transactions-detail.component';
import { BedTransactionsUpdateComponent } from './bed-transactions-update.component';

@Injectable({ providedIn: 'root' })
export class BedTransactionsResolve implements Resolve<IBedTransactions> {
  constructor(private service: BedTransactionsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBedTransactions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bedTransactions: HttpResponse<BedTransactions>) => {
          if (bedTransactions.body) {
            return of(bedTransactions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BedTransactions());
  }
}

export const bedTransactionsRoute: Routes = [
  {
    path: '',
    component: BedTransactionsComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'BedTransactions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BedTransactionsDetailComponent,
    resolve: {
      bedTransactions: BedTransactionsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BedTransactions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BedTransactionsUpdateComponent,
    resolve: {
      bedTransactions: BedTransactionsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BedTransactions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BedTransactionsUpdateComponent,
    resolve: {
      bedTransactions: BedTransactionsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BedTransactions',
    },
    canActivate: [UserRouteAccessService],
  },
];
