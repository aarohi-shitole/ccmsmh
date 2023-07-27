import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInventoryMaster, InventoryMaster } from 'app/shared/model/inventory-master.model';
import { InventoryMasterService } from './inventory-master.service';
import { IInventoryType } from 'app/shared/model/inventory-type.model';
import { InventoryTypeService } from 'app/entities/inventory-type/inventory-type.service';

@Component({
  selector: 'tvg-inventory-master-update',
  templateUrl: './inventory-master-update.component.html',
})
export class InventoryMasterUpdateComponent implements OnInit {
  isSaving = false;
  inventorytypes: IInventoryType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    volume: [],
    unit: [null, [Validators.required]],
    calulateVolume: [],
    dimensions: [],
    subTypeInd: [],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    inventoryTypeId: [],
  });

  constructor(
    protected inventoryMasterService: InventoryMasterService,
    protected inventoryTypeService: InventoryTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryMaster }) => {
      if (!inventoryMaster.id) {
        const today = moment().startOf('day');
        inventoryMaster.lastModified = today;
      }

      this.updateForm(inventoryMaster);

      this.inventoryTypeService.query().subscribe((res: HttpResponse<IInventoryType[]>) => (this.inventorytypes = res.body || []));
    });
  }

  updateForm(inventoryMaster: IInventoryMaster): void {
    this.editForm.patchValue({
      id: inventoryMaster.id,
      name: inventoryMaster.name,
      description: inventoryMaster.description,
      volume: inventoryMaster.volume,
      unit: inventoryMaster.unit,
      calulateVolume: inventoryMaster.calulateVolume,
      dimensions: inventoryMaster.dimensions,
      subTypeInd: inventoryMaster.subTypeInd,
      deleted: inventoryMaster.deleted,
      lastModified: inventoryMaster.lastModified ? inventoryMaster.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: inventoryMaster.lastModifiedBy,
      inventoryTypeId: inventoryMaster.inventoryTypeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventoryMaster = this.createFromForm();
    if (inventoryMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryMasterService.update(inventoryMaster));
    } else {
      this.subscribeToSaveResponse(this.inventoryMasterService.create(inventoryMaster));
    }
  }

  private createFromForm(): IInventoryMaster {
    return {
      ...new InventoryMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      volume: this.editForm.get(['volume'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      calulateVolume: this.editForm.get(['calulateVolume'])!.value,
      dimensions: this.editForm.get(['dimensions'])!.value,
      subTypeInd: this.editForm.get(['subTypeInd'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      inventoryTypeId: this.editForm.get(['inventoryTypeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryMaster>>): void {
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

  trackById(index: number, item: IInventoryType): any {
    return item.id;
  }
}
