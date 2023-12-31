import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { DivisionComponent } from './division.component';
import { DivisionDetailComponent } from './division-detail.component';
import { DivisionUpdateComponent } from './division-update.component';
import { DivisionDeleteDialogComponent } from './division-delete-dialog.component';
import { divisionRoute } from './division.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(divisionRoute)],
  declarations: [DivisionComponent, DivisionDetailComponent, DivisionUpdateComponent, DivisionDeleteDialogComponent],
  entryComponents: [DivisionDeleteDialogComponent],
})
export class CovidCareDivisionModule {}
