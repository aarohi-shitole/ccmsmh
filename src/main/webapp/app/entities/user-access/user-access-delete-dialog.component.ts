import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserAccess } from 'app/shared/model/user-access.model';
import { UserAccessService } from './user-access.service';

@Component({
  templateUrl: './user-access-delete-dialog.component.html',
})
export class UserAccessDeleteDialogComponent {
  userAccess?: IUserAccess;

  constructor(
    protected userAccessService: UserAccessService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userAccessService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userAccessListModification');
      this.activeModal.close();
    });
  }
}
