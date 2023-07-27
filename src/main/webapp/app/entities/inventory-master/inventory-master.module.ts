import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { InventoryMasterComponent } from './inventory-master.component';
import { InventoryMasterDetailComponent } from './inventory-master-detail.component';
import { InventoryMasterUpdateComponent } from './inventory-master-update.component';
import { InventoryMasterDeleteDialogComponent } from './inventory-master-delete-dialog.component';
import { inventoryMasterRoute } from './inventory-master.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(inventoryMasterRoute)],
  declarations: [
    InventoryMasterComponent,
    InventoryMasterDetailComponent,
    InventoryMasterUpdateComponent,
    InventoryMasterDeleteDialogComponent,
  ],
  entryComponents: [InventoryMasterDeleteDialogComponent],
})
export class CovidCareInventoryMasterModule {}
