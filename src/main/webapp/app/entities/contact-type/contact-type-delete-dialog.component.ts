import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactType } from 'app/shared/model/contact-type.model';
import { ContactTypeService } from './contact-type.service';

@Component({
  templateUrl: './contact-type-delete-dialog.component.html',
})
export class ContactTypeDeleteDialogComponent {
  contactType?: IContactType;

  constructor(
    protected contactTypeService: ContactTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contactTypeListModification');
      this.activeModal.close();
    });
  }
}
