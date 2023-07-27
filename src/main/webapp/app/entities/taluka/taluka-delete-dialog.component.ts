import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaluka } from 'app/shared/model/taluka.model';
import { TalukaService } from './taluka.service';

@Component({
  templateUrl: './taluka-delete-dialog.component.html',
})
export class TalukaDeleteDialogComponent {
  taluka?: ITaluka;

  constructor(protected talukaService: TalukaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.talukaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('talukaListModification');
      this.activeModal.close();
    });
  }
}
