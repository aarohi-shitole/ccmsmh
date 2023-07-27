import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { InventoryTypeComponent } from './inventory-type.component';
import { InventoryTypeDetailComponent } from './inventory-type-detail.component';
import { InventoryTypeUpdateComponent } from './inventory-type-update.component';
import { InventoryTypeDeleteDialogComponent } from './inventory-type-delete-dialog.component';
import { inventoryTypeRoute } from './inventory-type.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(inventoryTypeRoute)],
  declarations: [InventoryTypeComponent, InventoryTypeDetailComponent, InventoryTypeUpdateComponent, InventoryTypeDeleteDialogComponent],
  entryComponents: [InventoryTypeDeleteDialogComponent],
})
export class CovidCareInventoryTypeModule {}
