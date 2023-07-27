import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBedInventory } from 'app/shared/model/bed-inventory.model';
import { BedInventoryService } from './bed-inventory.service';

@Component({
  templateUrl: './bed-inventory-delete-dialog.component.html',
})
export class BedInventoryDeleteDialogComponent {
  bedInventory?: IBedInventory;

  constructor(
    protected bedInventoryService: BedInventoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bedInventoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bedInventoryListModification');
      this.activeModal.close();
    });
  }
}
