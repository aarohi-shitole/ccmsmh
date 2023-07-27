import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryUsed } from 'app/shared/model/inventory-used.model';
import { InventoryUsedService } from './inventory-used.service';

@Component({
  templateUrl: './inventory-used-delete-dialog.component.html',
})
export class InventoryUsedDeleteDialogComponent {
  inventoryUsed?: IInventoryUsed;

  constructor(
    protected inventoryUsedService: InventoryUsedService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventoryUsedService.delete(id).subscribe(() => {
      this.eventManager.broadcast('inventoryUsedListModification');
      this.activeModal.close();
    });
  }
}
