import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { SecurityRoleComponent } from './security-role.component';
import { SecurityRoleDetailComponent } from './security-role-detail.component';
import { SecurityRoleUpdateComponent } from './security-role-update.component';
import { SecurityRoleDeleteDialogComponent } from './security-role-delete-dialog.component';
import { securityRoleRoute } from './security-role.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(securityRoleRoute)],
  declarations: [SecurityRoleComponent, SecurityRoleDetailComponent, SecurityRoleUpdateComponent, SecurityRoleDeleteDialogComponent],
  entryComponents: [SecurityRoleDeleteDialogComponent],
})
export class CovidCareSecurityRoleModule {}
