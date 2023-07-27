import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { BedInventoryComponent } from './bed-inventory.component';
import { BedInventoryDetailComponent } from './bed-inventory-detail.component';
import { BedInventoryUpdateComponent } from './bed-inventory-update.component';
import { BedInventoryDeleteDialogComponent } from './bed-inventory-delete-dialog.component';
import { bedInventoryRoute } from './bed-inventory.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(bedInventoryRoute)],
  declarations: [BedInventoryComponent, BedInventoryDetailComponent, BedInventoryUpdateComponent, BedInventoryDeleteDialogComponent],
  entryComponents: [BedInventoryDeleteDialogComponent],
})
export class CovidCareBedInventoryModule {}
