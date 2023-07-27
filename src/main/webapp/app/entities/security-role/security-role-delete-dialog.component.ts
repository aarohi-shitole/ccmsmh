import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISecurityRole } from 'app/shared/model/security-role.model';
import { SecurityRoleService } from './security-role.service';

@Component({
  templateUrl: './security-role-delete-dialog.component.html',
})
export class SecurityRoleDeleteDialogComponent {
  securityRole?: ISecurityRole;

  constructor(
    protected securityRoleService: SecurityRoleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.securityRoleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('securityRoleListModification');
      this.activeModal.close();
    });
  }
}
