import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { TripDetailsComponent } from './trip-details.component';
import { TripDetailsDetailComponent } from './trip-details-detail.component';
import { TripDetailsUpdateComponent } from './trip-details-update.component';
import { TripDetailsDeleteDialogComponent } from './trip-details-delete-dialog.component';
import { tripDetailsRoute } from './trip-details.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(tripDetailsRoute)],
  declarations: [TripDetailsComponent, TripDetailsDetailComponent, TripDetailsUpdateComponent, TripDetailsDeleteDialogComponent],
  entryComponents: [TripDetailsDeleteDialogComponent],
})
export class CovidCareTripDetailsModule {}
