import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CovidCareSharedModule } from 'app/shared/shared.module';
import { BedTransactionsComponent } from './bed-transactions.component';
import { BedTransactionsDetailComponent } from './bed-transactions-detail.component';
import { BedTransactionsUpdateComponent } from './bed-transactions-update.component';
import { BedTransactionsDeleteDialogComponent } from './bed-transactions-delete-dialog.component';
import { bedTransactionsRoute } from './bed-transactions.route';

@NgModule({
  imports: [CovidCareSharedModule, RouterModule.forChild(bedTransactionsRoute)],
  declarations: [
    BedTransactionsComponent,
    BedTransactionsDetailComponent,
    BedTransactionsUpdateComponent,
    BedTransactionsDeleteDialogComponent,
  ],
  entryComponents: [BedTransactionsDeleteDialogComponent],
})
export class CovidCareBedTransactionsModule {}
