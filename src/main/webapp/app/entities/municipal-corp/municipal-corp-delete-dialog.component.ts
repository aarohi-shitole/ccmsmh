import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMunicipalCorp } from 'app/shared/model/municipal-corp.model';
import { MunicipalCorpService } from './municipal-corp.service';

@Component({
  templateUrl: './municipal-corp-delete-dialog.component.html',
})
export class MunicipalCorpDeleteDialogComponent {
  municipalCorp?: IMunicipalCorp;

  constructor(
    protected municipalCorpService: MunicipalCorpService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.municipalCorpService.delete(id).subscribe(() => {
      this.eventManager.broadcast('municipalCorpListModification');
      this.activeModal.close();
    });
  }
}
