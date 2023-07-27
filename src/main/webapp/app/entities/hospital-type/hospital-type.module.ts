import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { HospitalTypeComponent } from './hospital-type.component';
import { HospitalTypeDetailComponent } from './hospital-type-detail.component';
import { HospitalTypeUpdateComponent } from './hospital-type-update.component';
import { HospitalTypeDeleteDialogComponent } from './hospital-type-delete-dialog.component';
import { hospitalTypeRoute } from './hospital-type.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(hospitalTypeRoute)],
  declarations: [HospitalTypeComponent, HospitalTypeDetailComponent, HospitalTypeUpdateComponent, HospitalTypeDeleteDialogComponent],
  entryComponents: [HospitalTypeDeleteDialogComponent],
})
export class CovidCareHospitalTypeModule {}
