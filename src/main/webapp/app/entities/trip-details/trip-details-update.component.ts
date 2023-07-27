import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITripDetails, TripDetails } from 'app/shared/model/trip-details.model';
import { TripDetailsService } from './trip-details.service';
import { ISupplier } from 'app/shared/model/supplier.model';
import { SupplierService } from 'app/entities/supplier/supplier.service';
import { IHospital } from 'app/shared/model/hospital.model';
import { HospitalService } from 'app/entities/hospital/hospital.service';
import { ITransactions } from 'app/shared/model/transactions.model';
import { TransactionsService } from 'app/entities/transactions/transactions.service';
import { ITrip } from 'app/shared/model/trip.model';
import { TripService } from 'app/entities/trip/trip.service';

type SelectableEntity = ISupplier | IHospital | ITransactions | ITrip;

@Component({
  selector: 'tvg-trip-details-update',
  templateUrl: './trip-details-update.component.html',
})
export class TripDetailsUpdateComponent implements OnInit {
  isSaving = false;
  suppliers: ISupplier[] = [];
  hospitals: IHospital[] = [];
  transactions: ITransactions[] = [];
  trips: ITrip[] = [];

  editForm = this.fb.group({
    id: [],
    stockSent: [null, [Validators.required]],
    stockRec: [],
    status: [null, [Validators.required]],
    createdDate: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    lastModified: [],
    lastModifiedBy: [],
    supplierId: [],
    hospitalId: [],
    transactionsId: [],
    tripId: [],
  });

  constructor(
    protected tripDetailsService: TripDetailsService,
    protected supplierService: SupplierService,
    protected hospitalService: HospitalService,
    protected transactionsService: TransactionsService,
    protected tripService: TripService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tripDetails }) => {
      if (!tripDetails.id) {
        const today = moment().startOf('day');
        tripDetails.createdDate = today;
        tripDetails.lastModified = today;
      }

      this.updateForm(tripDetails);

      this.supplierService.query().subscribe((res: HttpResponse<ISupplier[]>) => (this.suppliers = res.body || []));

      this.hospitalService.query().subscribe((res: HttpResponse<IHospital[]>) => (this.hospitals = res.body || []));

      this.transactionsService.query().subscribe((res: HttpResponse<ITransactions[]>) => (this.transactions = res.body || []));

      this.tripService.query().subscribe((res: HttpResponse<ITrip[]>) => (this.trips = res.body || []));
    });
  }

  updateForm(tripDetails: ITripDetails): void {
    this.editForm.patchValue({
      id: tripDetails.id,
      stockSent: tripDetails.stockSent,
      stockRec: tripDetails.stockRec,
      status: tripDetails.status,
      createdDate: tripDetails.createdDate ? tripDetails.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: tripDetails.createdBy,
      lastModified: tripDetails.lastModified ? tripDetails.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: tripDetails.lastModifiedBy,
      supplierId: tripDetails.supplierId,
      hospitalId: tripDetails.hospitalId,
      transactionsId: tripDetails.transactionsId,
      tripId: tripDetails.tripId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tripDetails = this.createFromForm();
    if (tripDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.tripDetailsService.update(tripDetails));
    } else {
      this.subscribeToSaveResponse(this.tripDetailsService.create(tripDetails));
    }
  }

  private createFromForm(): ITripDetails {
    return {
      ...new TripDetails(),
      id: this.editForm.get(['id'])!.value,
      stockSent: this.editForm.get(['stockSent'])!.value,
      stockRec: this.editForm.get(['stockRec'])!.value,
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
      hospitalId: this.editForm.get(['hospitalId'])!.value,
      transactionsId: this.editForm.get(['transactionsId'])!.value,
      tripId: this.editForm.get(['tripId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITripDetails>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
