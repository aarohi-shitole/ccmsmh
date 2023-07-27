import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInventoryMaster, InventoryMaster } from 'app/shared/model/inventory-master.model';
import { InventoryMasterService } from './inventory-master.service';
import { InventoryMasterComponent } from './inventory-master.component';
import { InventoryMasterDetailComponent } from './inventory-master-detail.component';
import { InventoryMasterUpdateComponent } from './inventory-master-update.component';

@Injectable({ providedIn: 'root' })
export class InventoryMasterResolve implements Resolve<IInventoryMaster> {
  constructor(private service: InventoryMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventoryMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((inventoryMaster: HttpResponse<InventoryMaster>) => {
          if (inventoryMaster.body) {
            return of(inventoryMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InventoryMaster());
  }
}

export const inventoryMasterRoute: Routes = [
  {
    path: '',
    component: InventoryMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'InventoryMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InventoryMasterDetailComponent,
    resolve: {
      inventoryMaster: InventoryMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InventoryMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InventoryMasterUpdateComponent,
    resolve: {
      inventoryMaster: InventoryMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InventoryMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InventoryMasterUpdateComponent,
    resolve: {
      inventoryMaster: InventoryMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InventoryMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
