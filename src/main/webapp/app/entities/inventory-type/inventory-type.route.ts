import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInventoryType, InventoryType } from 'app/shared/model/inventory-type.model';
import { InventoryTypeService } from './inventory-type.service';
import { InventoryTypeComponent } from './inventory-type.component';
import { InventoryTypeDetailComponent } from './inventory-type-detail.component';
import { InventoryTypeUpdateComponent } from './inventory-type-update.component';

@Injectable({ providedIn: 'root' })
export class InventoryTypeResolve implements Resolve<IInventoryType> {
  constructor(private service: InventoryTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventoryType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((inventoryType: HttpResponse<InventoryType>) => {
          if (inventoryType.body) {
            return of(inventoryType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InventoryType());
  }
}

export const inventoryTypeRoute: Routes = [
  {
    path: '',
    component: InventoryTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'InventoryTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InventoryTypeDetailComponent,
    resolve: {
      inventoryType: InventoryTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InventoryTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InventoryTypeUpdateComponent,
    resolve: {
      inventoryType: InventoryTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InventoryTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InventoryTypeUpdateComponent,
    resolve: {
      inventoryType: InventoryTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InventoryTypes',
    },
    canActivate: [UserRouteAccessService],
  },
];
