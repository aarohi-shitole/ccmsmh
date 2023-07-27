import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInventory, Inventory } from 'app/shared/model/inventory.model';
import { InventoryService } from './inventory.service';
import { InventoryComponent } from './inventory.component';
import { InventoryDetailComponent } from './inventory-detail.component';
import { InventoryUpdateComponent } from './inventory-update.component';

@Injectable({ providedIn: 'root' })
export class InventoryResolve implements Resolve<IInventory> {
  constructor(private service: InventoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((inventory: HttpResponse<Inventory>) => {
          if (inventory.body) {
            return of(inventory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Inventory());
  }
}

export const inventoryRoute: Routes = [
  {
    path: '',
    component: InventoryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Inventories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InventoryDetailComponent,
    resolve: {
      inventory: InventoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Inventories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InventoryUpdateComponent,
    resolve: {
      inventory: InventoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Inventories',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InventoryUpdateComponent,
    resolve: {
      inventory: InventoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Inventories',
    },
    canActivate: [UserRouteAccessService],
  },
];
