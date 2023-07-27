import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryType } from 'app/shared/model/inventory-type.model';
import { InventoryTypeService } from './inventory-type.service';

@Component({
  templateUrl: './inventory-type-delete-dialog.component.html',
})
export class InventoryTypeDeleteDialogComponent {
  inventoryType?: IInventoryType;

  constructor(
    protected inventoryTypeService: InventoryTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventoryTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('inventoryTypeListModification');
      this.activeModal.close();
    });
  }
}
