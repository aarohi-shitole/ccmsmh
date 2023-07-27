import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { AuditTypeComponent } from './audit-type.component';
import { AuditTypeDetailComponent } from './audit-type-detail.component';
import { AuditTypeUpdateComponent } from './audit-type-update.component';
import { AuditTypeDeleteDialogComponent } from './audit-type-delete-dialog.component';
import { auditTypeRoute } from './audit-type.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(auditTypeRoute)],
  declarations: [AuditTypeComponent, AuditTypeDetailComponent, AuditTypeUpdateComponent, AuditTypeDeleteDialogComponent],
  entryComponents: [AuditTypeDeleteDialogComponent],
})
export class CovidCareAuditTypeModule {}
