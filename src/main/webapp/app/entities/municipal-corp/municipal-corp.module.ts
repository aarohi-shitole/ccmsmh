import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { MunicipalCorpComponent } from './municipal-corp.component';
import { MunicipalCorpDetailComponent } from './municipal-corp-detail.component';
import { MunicipalCorpUpdateComponent } from './municipal-corp-update.component';
import { MunicipalCorpDeleteDialogComponent } from './municipal-corp-delete-dialog.component';
import { municipalCorpRoute } from './municipal-corp.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(municipalCorpRoute)],
  declarations: [MunicipalCorpComponent, MunicipalCorpDetailComponent, MunicipalCorpUpdateComponent, MunicipalCorpDeleteDialogComponent],
  entryComponents: [MunicipalCorpDeleteDialogComponent],
})
export class CovidCareMunicipalCorpModule {}
