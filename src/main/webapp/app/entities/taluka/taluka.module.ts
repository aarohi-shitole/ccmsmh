import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { TalukaComponent } from './taluka.component';
import { TalukaDetailComponent } from './taluka-detail.component';
import { TalukaUpdateComponent } from './taluka-update.component';
import { TalukaDeleteDialogComponent } from './taluka-delete-dialog.component';
import { talukaRoute } from './taluka.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(talukaRoute)],
  declarations: [TalukaComponent, TalukaDetailComponent, TalukaUpdateComponent, TalukaDeleteDialogComponent],
  entryComponents: [TalukaDeleteDialogComponent],
})
export class CovidCareTalukaModule {}
