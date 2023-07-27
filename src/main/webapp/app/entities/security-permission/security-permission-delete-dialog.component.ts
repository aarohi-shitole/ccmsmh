import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISecurityPermission } from 'app/shared/model/security-permission.model';
import { SecurityPermissionService } from './security-permission.service';

@Component({
  templateUrl: './security-permission-delete-dialog.component.html',
})
export class SecurityPermissionDeleteDialogComponent {
  securityPermission?: ISecurityPermission;

  constructor(
    protected securityPermissionService: SecurityPermissionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.securityPermissionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('securityPermissionListModification');
      this.activeModal.close();
    });
  }
}
