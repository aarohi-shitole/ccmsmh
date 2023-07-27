import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPatientInfo } from 'app/shared/model/patient-info.model';
import { PatientInfoService } from './patient-info.service';

@Component({
  templateUrl: './patient-info-delete-dialog.component.html',
})
export class PatientInfoDeleteDialogComponent {
  patientInfo?: IPatientInfo;

  constructor(
    protected patientInfoService: PatientInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.patientInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('patientInfoListModification');
      this.activeModal.close();
    });
  }
}
