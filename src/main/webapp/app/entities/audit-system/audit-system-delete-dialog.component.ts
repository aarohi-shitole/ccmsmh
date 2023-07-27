import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAuditSystem } from 'app/shared/model/audit-system.model';
import { AuditSystemService } from './audit-system.service';

@Component({
  templateUrl: './audit-system-delete-dialog.component.html',
})
export class AuditSystemDeleteDialogComponent {
  auditSystem?: IAuditSystem;

  constructor(
    protected auditSystemService: AuditSystemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auditSystemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('auditSystemListModification');
      this.activeModal.close();
    });
  }
}
