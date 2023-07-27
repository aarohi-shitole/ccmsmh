import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBedType } from 'app/shared/model/bed-type.model';
import { BedTypeService } from './bed-type.service';

@Component({
  templateUrl: './bed-type-delete-dialog.component.html',
})
export class BedTypeDeleteDialogComponent {
  bedType?: IBedType;

  constructor(protected bedTypeService: BedTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bedTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bedTypeListModification');
      this.activeModal.close();
    });
  }
}
