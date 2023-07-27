import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISecurityUser } from 'app/shared/model/security-user.model';
import { SecurityUserService } from './security-user.service';

@Component({
  templateUrl: './security-user-delete-dialog.component.html',
})
export class SecurityUserDeleteDialogComponent {
  securityUser?: ISecurityUser;

  constructor(
    protected securityUserService: SecurityUserService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.securityUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('securityUserListModification');
      this.activeModal.close();
    });
  }
}
