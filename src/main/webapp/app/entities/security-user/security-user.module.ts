import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { SecurityUserComponent } from './security-user.component';
import { SecurityUserDetailComponent } from './security-user-detail.component';
import { SecurityUserUpdateComponent } from './security-user-update.component';
import { SecurityUserDeleteDialogComponent } from './security-user-delete-dialog.component';
import { securityUserRoute } from './security-user.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(securityUserRoute)],
  declarations: [SecurityUserComponent, SecurityUserDetailComponent, SecurityUserUpdateComponent, SecurityUserDeleteDialogComponent],
  entryComponents: [SecurityUserDeleteDialogComponent],
})
export class CovidCareSecurityUserModule {}
