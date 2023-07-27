import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { AuditSystemComponent } from './audit-system.component';
import { AuditSystemDetailComponent } from './audit-system-detail.component';
import { AuditSystemUpdateComponent } from './audit-system-update.component';
import { AuditSystemDeleteDialogComponent } from './audit-system-delete-dialog.component';
import { auditSystemRoute } from './audit-system.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(auditSystemRoute)],
  declarations: [AuditSystemComponent, AuditSystemDetailComponent, AuditSystemUpdateComponent, AuditSystemDeleteDialogComponent],
  entryComponents: [AuditSystemDeleteDialogComponent],
})
export class CovidCareAuditSystemModule {}
