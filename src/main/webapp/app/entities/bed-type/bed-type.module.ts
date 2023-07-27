import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { BedTypeComponent } from './bed-type.component';
import { BedTypeDetailComponent } from './bed-type-detail.component';
import { BedTypeUpdateComponent } from './bed-type-update.component';
import { BedTypeDeleteDialogComponent } from './bed-type-delete-dialog.component';
import { bedTypeRoute } from './bed-type.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(bedTypeRoute)],
  declarations: [BedTypeComponent, BedTypeDetailComponent, BedTypeUpdateComponent, BedTypeDeleteDialogComponent],
  entryComponents: [BedTypeDeleteDialogComponent],
})
export class CovidCareBedTypeModule {}
