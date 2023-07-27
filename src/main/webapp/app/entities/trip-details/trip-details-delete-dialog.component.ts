import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITripDetails } from 'app/shared/model/trip-details.model';
import { TripDetailsService } from './trip-details.service';

@Component({
  templateUrl: './trip-details-delete-dialog.component.html',
})
export class TripDetailsDeleteDialogComponent {
  tripDetails?: ITripDetails;

  constructor(
    protected tripDetailsService: TripDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tripDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tripDetailsListModification');
      this.activeModal.close();
    });
  }
}
