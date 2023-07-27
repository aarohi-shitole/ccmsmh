import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { PatientInfoComponent } from './patient-info.component';
import { PatientInfoDetailComponent } from './patient-info-detail.component';
import { PatientInfoUpdateComponent } from './patient-info-update.component';
import { PatientInfoDeleteDialogComponent } from './patient-info-delete-dialog.component';
import { patientInfoRoute } from './patient-info.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(patientInfoRoute)],
  declarations: [PatientInfoComponent, PatientInfoDetailComponent, PatientInfoUpdateComponent, PatientInfoDeleteDialogComponent],
  entryComponents: [PatientInfoDeleteDialogComponent],
})
export class CovidCarePatientInfoModule {}
