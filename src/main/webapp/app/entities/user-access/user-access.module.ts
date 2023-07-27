import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { UserAccessComponent } from './user-access.component';
import { UserAccessDetailComponent } from './user-access-detail.component';
import { UserAccessUpdateComponent } from './user-access-update.component';
import { UserAccessDeleteDialogComponent } from './user-access-delete-dialog.component';
import { userAccessRoute } from './user-access.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(userAccessRoute)],
  declarations: [UserAccessComponent, UserAccessDetailComponent, UserAccessUpdateComponent, UserAccessDeleteDialogComponent],
  entryComponents: [UserAccessDeleteDialogComponent],
})
export class CovidCareUserAccessModule {}
