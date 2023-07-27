import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInventoryUsed, InventoryUsed } from 'app/shared/model/inventory-used.model';
import { InventoryUsedService } from './inventory-used.service';
import { InventoryUsedComponent } from './inventory-used.component';
import { InventoryUsedDetailComponent } from './inventory-used-detail.component';
import { InventoryUsedUpdateComponent } from './inventory-used-update.component';

@Injectable({ providedIn: 'root' })
export class InventoryUsedResolve implements Resolve<IInventoryUsed> {
  constructor(private service: InventoryUsedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventoryUsed> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((inventoryUsed: HttpResponse<InventoryUsed>) => {
          if (inventoryUsed.body) {
            return of(inventoryUsed.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InventoryUsed());
  }
}

export const inventoryUsedRoute: Routes = [
  {
    path: '',
    component: InventoryUsedComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'InventoryUseds',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InventoryUsedDetailComponent,
    resolve: {
      inventoryUsed: InventoryUsedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InventoryUseds',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InventoryUsedUpdateComponent,
    resolve: {
      inventoryUsed: InventoryUsedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InventoryUseds',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InventoryUsedUpdateComponent,
    resolve: {
      inventoryUsed: InventoryUsedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InventoryUseds',
    },
    canActivate: [UserRouteAccessService],
  },
];
