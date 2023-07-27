import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITrip, Trip } from 'app/shared/model/trip.model';
import { TripService } from './trip.service';
import { ISupplier } from 'app/shared/model/supplier.model';
import { SupplierService } from 'app/entities/supplier/supplier.service';

@Component({
  selector: 'tvg-trip-update',
  templateUrl: './trip-update.component.html',
})
export class TripUpdateComponent implements OnInit {
  isSaving = false;
  suppliers: ISupplier[] = [];

  editForm = this.fb.group({
    id: [],
    trackingNo: [null, [Validators.required]],
    mobaId: [null, [Validators.required]],
    numberPlate: [null, [Validators.required]],
    stock: [null, [Validators.required]],
    status: [null, [Validators.required]],
    createdDate: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    lastModified: [],
    lastModifiedBy: [],
    supplierId: [],
  });

  constructor(
    protected tripService: TripService,
    protected supplierService: SupplierService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trip }) => {
      if (!trip.id) {
        const today = moment().startOf('day');
        trip.createdDate = today;
        trip.lastModified = today;
      }

      this.updateForm(trip);

      this.supplierService.query().subscribe((res: HttpResponse<ISupplier[]>) => (this.suppliers = res.body || []));
    });
  }

  updateForm(trip: ITrip): void {
    this.editForm.patchValue({
      id: trip.id,
      trackingNo: trip.trackingNo,
      mobaId: trip.mobaId,
      numberPlate: trip.numberPlate,
      stock: trip.stock,
      status: trip.status,
      createdDate: trip.createdDate ? trip.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: trip.createdBy,
      lastModified: trip.lastModified ? trip.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: trip.lastModifiedBy,
      supplierId: trip.supplierId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trip = this.createFromForm();
    if (trip.id !== undefined) {
      this.subscribeToSaveResponse(this.tripService.update(trip));
    } else {
      this.subscribeToSaveResponse(this.tripService.create(trip));
    }
  }

  private createFromForm(): ITrip {
    return {
      ...new Trip(),
      id: this.editForm.get(['id'])!.value,
      trackingNo: this.editForm.get(['trackingNo'])!.value,
      mobaId: this.editForm.get(['mobaId'])!.value,
      numberPlate: this.editForm.get(['numberPlate'])!.value,
      stock: this.editForm.get(['stock'])!.value,
      status: this.editForm.get(['status'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      supplierId: this.editForm.get(['supplierId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrip>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ISupplier): any {
    return item.id;
  }
}
