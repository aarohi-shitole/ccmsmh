import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHospitalType } from 'app/shared/model/hospital-type.model';
import { HospitalTypeService } from './hospital-type.service';

@Component({
  templateUrl: './hospital-type-delete-dialog.component.html',
})
export class HospitalTypeDeleteDialogComponent {
  hospitalType?: IHospitalType;

  constructor(
    protected hospitalTypeService: HospitalTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hospitalTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('hospitalTypeListModification');
      this.activeModal.close();
    });
  }
}
