import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { SecurityPermissionComponent } from './security-permission.component';
import { SecurityPermissionDetailComponent } from './security-permission-detail.component';
import { SecurityPermissionUpdateComponent } from './security-permission-update.component';
import { SecurityPermissionDeleteDialogComponent } from './security-permission-delete-dialog.component';
import { securityPermissionRoute } from './security-permission.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(securityPermissionRoute)],
  declarations: [
    SecurityPermissionComponent,
    SecurityPermissionDetailComponent,
    SecurityPermissionUpdateComponent,
    SecurityPermissionDeleteDialogComponent,
  ],
  entryComponents: [SecurityPermissionDeleteDialogComponent],
})
export class CovidCareSecurityPermissionModule {}
