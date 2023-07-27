import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryMaster } from 'app/shared/model/inventory-master.model';
import { InventoryMasterService } from './inventory-master.service';

@Component({
  templateUrl: './inventory-master-delete-dialog.component.html',
})
export class InventoryMasterDeleteDialogComponent {
  inventoryMaster?: IInventoryMaster;

  constructor(
    protected inventoryMasterService: InventoryMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventoryMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('inventoryMasterListModification');
      this.activeModal.close();
    });
  }
}
