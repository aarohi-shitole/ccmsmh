import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBedTransactions } from 'app/shared/model/bed-transactions.model';
import { BedTransactionsService } from './bed-transactions.service';

@Component({
  templateUrl: './bed-transactions-delete-dialog.component.html',
})
export class BedTransactionsDeleteDialogComponent {
  bedTransactions?: IBedTransactions;

  constructor(
    protected bedTransactionsService: BedTransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bedTransactionsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bedTransactionsListModification');
      this.activeModal.close();
    });
  }
}
