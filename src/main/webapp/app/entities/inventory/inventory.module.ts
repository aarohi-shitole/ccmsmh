import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { InventoryComponent } from './inventory.component';
import { InventoryDetailComponent } from './inventory-detail.component';
import { InventoryUpdateComponent } from './inventory-update.component';
import { InventoryDeleteDialogComponent } from './inventory-delete-dialog.component';
import { inventoryRoute } from './inventory.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(inventoryRoute)],
  declarations: [InventoryComponent, InventoryDetailComponent, InventoryUpdateComponent, InventoryDeleteDialogComponent],
  entryComponents: [InventoryDeleteDialogComponent],
})
export class CovidCareInventoryModule {}
