import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IState } from 'app/shared/model/state.model';
import { StateService } from './state.service';

@Component({
  templateUrl: './state-delete-dialog.component.html',
})
export class StateDeleteDialogComponent {
  state?: IState;

  constructor(protected stateService: StateService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('stateListModification');
      this.activeModal.close();
    });
  }
}
