import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAuditType } from 'app/shared/model/audit-type.model';
import { AuditTypeService } from './audit-type.service';

@Component({
  templateUrl: './audit-type-delete-dialog.component.html',
})
export class AuditTypeDeleteDialogComponent {
  auditType?: IAuditType;

  constructor(protected auditTypeService: AuditTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auditTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('auditTypeListModification');
      this.activeModal.close();
    });
  }
}
