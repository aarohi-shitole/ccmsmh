import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { StateComponent } from './state.component';
import { StateDetailComponent } from './state-detail.component';
import { StateUpdateComponent } from './state-update.component';
import { StateDeleteDialogComponent } from './state-delete-dialog.component';
import { stateRoute } from './state.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(stateRoute)],
  declarations: [StateComponent, StateDetailComponent, StateUpdateComponent, StateDeleteDialogComponent],
  entryComponents: [StateDeleteDialogComponent],
})
export class CovidCareStateModule {}
