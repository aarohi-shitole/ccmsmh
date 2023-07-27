import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBedInventory, BedInventory } from 'app/shared/model/bed-inventory.model';
import { BedInventoryService } from './bed-inventory.service';
import { IBedType } from 'app/shared/model/bed-type.model';
import { BedTypeService } from 'app/entities/bed-type/bed-type.service';
import { IHospital } from 'app/shared/model/hospital.model';
import { HospitalService } from 'app/entities/hospital/hospital.service';

type SelectableEntity = IBedType | IHospital;

@Component({
  selector: 'tvg-bed-inventory-update',
  templateUrl: './bed-inventory-update.component.html',
})
export class BedInventoryUpdateComponent implements OnInit {
  isSaving = false;
  bedtypes: IBedType[] = [];
  hospitals: IHospital[] = [];

  editForm = this.fb.group({
    id: [],
    bedCount: [null, [Validators.required]],
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
    protected bedInventoryService: BedInventoryService,
    protected bedTypeService: BedTypeService,
    protected hospitalService: HospitalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bedInventory }) => {
      if (!bedInventory.id) {
        const today = moment().startOf('day');
        bedInventory.lastModified = today;
      }

      this.updateForm(bedInventory);

      this.bedTypeService.query().subscribe((res: HttpResponse<IBedType[]>) => (this.bedtypes = res.body || []));

      this.hospitalService.query().subscribe((res: HttpResponse<IHospital[]>) => (this.hospitals = res.body || []));
    });
  }

  updateForm(bedInventory: IBedInventory): void {
    this.editForm.patchValue({
      id: bedInventory.id,
      bedCount: bedInventory.bedCount,
      occupied: bedInventory.occupied,
      onCylinder: bedInventory.onCylinder,
      onLMO: bedInventory.onLMO,
      onConcentrators: bedInventory.onConcentrators,
      lastModified: bedInventory.lastModified ? bedInventory.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: bedInventory.lastModifiedBy,
      bedTypeId: bedInventory.bedTypeId,
      hospitalId: bedInventory.hospitalId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bedInventory = this.createFromForm();
    if (bedInventory.id !== undefined) {
      this.subscribeToSaveResponse(this.bedInventoryService.update(bedInventory));
    } else {
      this.subscribeToSaveResponse(this.bedInventoryService.create(bedInventory));
    }
  }

  private createFromForm(): IBedInventory {
    return {
      ...new BedInventory(),
      id: this.editForm.get(['id'])!.value,
      bedCount: this.editForm.get(['bedCount'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBedInventory>>): void {
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
