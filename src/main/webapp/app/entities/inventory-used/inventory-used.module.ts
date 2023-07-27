import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { InventoryUsedComponent } from './inventory-used.component';
import { InventoryUsedDetailComponent } from './inventory-used-detail.component';
import { InventoryUsedUpdateComponent } from './inventory-used-update.component';
import { InventoryUsedDeleteDialogComponent } from './inventory-used-delete-dialog.component';
import { inventoryUsedRoute } from './inventory-used.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(inventoryUsedRoute)],
  declarations: [InventoryUsedComponent, InventoryUsedDetailComponent, InventoryUsedUpdateComponent, InventoryUsedDeleteDialogComponent],
  entryComponents: [InventoryUsedDeleteDialogComponent],
})
export class CovidCareInventoryUsedModule {}
