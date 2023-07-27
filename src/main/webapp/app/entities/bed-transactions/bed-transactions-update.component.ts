import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBedTransactions, BedTransactions } from 'app/shared/model/bed-transactions.model';
import { BedTransactionsService } from './bed-transactions.service';
import { IBedType } from 'app/shared/model/bed-type.model';
import { BedTypeService } from 'app/entities/bed-type/bed-type.service';
import { IHospital } from 'app/shared/model/hospital.model';
import { HospitalService } from 'app/entities/hospital/hospital.service';

type SelectableEntity = IBedType | IHospital;

@Component({
  selector: 'tvg-bed-transactions-update',
  templateUrl: './bed-transactions-update.component.html',
})
export class BedTransactionsUpdateComponent implements OnInit {
  isSaving = false;
  bedtypes: IBedType[] = [];
  hospitals: IHospital[] = [];

  editForm = this.fb.group({
    id: [],
    occupied: [null, [Validators.required]],
    onCylinder: [],
    onLMO: [],
    onConcentrators: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    bedTypeId: [],
    hospitalId: [],
  });

  constructor(
    protected bedTransactionsService: BedTransactionsService,
    protected bedTypeService: BedTypeService,
    protected hospitalService: HospitalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bedTransactions }) => {
      if (!bedTransactions.id) {
        const today = moment().startOf('day');
        bedTransactions.lastModified = today;
      }

      this.updateForm(bedTransactions);

      this.bedTypeService.query().subscribe((res: HttpResponse<IBedType[]>) => (this.bedtypes = res.body || []));

      this.hospitalService.query().subscribe((res: HttpResponse<IHospital[]>) => (this.hospitals = res.body || []));
    });
  }

  updateForm(bedTransactions: IBedTransactions): void {
    this.editForm.patchValue({
      id: bedTransactions.id,
      occupied: bedTransactions.occupied,
      onCylinder: bedTransactions.onCylinder,
      onLMO: bedTransactions.onLMO,
      onConcentrators: bedTransactions.onConcentrators,
      lastModified: bedTransactions.lastModified ? bedTransactions.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: bedTransactions.lastModifiedBy,
      bedTypeId: bedTransactions.bedTypeId,
      hospitalId: bedTransactions.hospitalId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bedTransactions = this.createFromForm();
    if (bedTransactions.id !== undefined) {
      this.subscribeToSaveResponse(this.bedTransactionsService.update(bedTransactions));
    } else {
      this.subscribeToSaveResponse(this.bedTransactionsService.create(bedTransactions));
    }
  }

  private createFromForm(): IBedTransactions {
    return {
      ...new BedTransactions(),
      id: this.editForm.get(['id'])!.value,
      occupied: this.editForm.get(['occupied'])!.value,
      onCylinder: this.editForm.get(['onCylinder'])!.value,
      onLMO: this.editForm.get(['onLMO'])!.value,
      onConcentrators: this.editForm.get(['onConcentrators'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      bedTypeId: this.editForm.get(['bedTypeId'])!.value,
      hospitalId: this.editForm.get(['hospitalId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBedTransactions>>): void {
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
