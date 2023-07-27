import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInventory, Inventory } from 'app/shared/model/inventory.model';
import { InventoryService } from './inventory.service';
import { IInventoryMaster } from 'app/shared/model/inventory-master.model';
import { InventoryMasterService } from 'app/entities/inventory-master/inventory-master.service';
import { ISupplier } from 'app/shared/model/supplier.model';
import { SupplierService } from 'app/entities/supplier/supplier.service';
import { IHospital } from 'app/shared/model/hospital.model';
import { HospitalService } from 'app/entities/hospital/hospital.service';

type SelectableEntity = IInventoryMaster | ISupplier | IHospital;

@Component({
  selector: 'tvg-inventory-update',
  templateUrl: './inventory-update.component.html',
})
export class InventoryUpdateComponent implements OnInit {
  isSaving = false;
  inventorymasters: IInventoryMaster[] = [];
  suppliers: ISupplier[] = [];
  hospitals: IHospital[] = [];

  editForm = this.fb.group({
    id: [],
    stock: [null, [Validators.required]],
    capcity: [],
    installedCapcity: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    inventoryMasterId: [],
    supplierId: [],
    hospitalId: [],
  });

  constructor(
    protected inventoryService: InventoryService,
    protected inventoryMasterService: InventoryMasterService,
    protected supplierService: SupplierService,
    protected hospitalService: HospitalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventory }) => {
      if (!inventory.id) {
        const today = moment().startOf('day');
        inventory.lastModified = today;
      }

      this.updateForm(inventory);

      this.inventoryMasterService.query().subscribe((res: HttpResponse<IInventoryMaster[]>) => (this.inventorymasters = res.body || []));

      this.supplierService.query().subscribe((res: HttpResponse<ISupplier[]>) => (this.suppliers = res.body || []));

      this.hospitalService.query().subscribe((res: HttpResponse<IHospital[]>) => (this.hospitals = res.body || []));
    });
  }

  updateForm(inventory: IInventory): void {
    this.editForm.patchValue({
      id: inventory.id,
      stock: inventory.stock,
      capcity: inventory.capcity,
      installedCapcity: inventory.installedCapcity,
      lastModified: inventory.lastModified ? inventory.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: inventory.lastModifiedBy,
      inventoryMasterId: inventory.inventoryMasterId,
      supplierId: inventory.supplierId,
      hospitalId: inventory.hospitalId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventory = this.createFromForm();
    if (inventory.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryService.update(inventory));
    } else {
      this.subscribeToSaveResponse(this.inventoryService.create(inventory));
    }
  }

  private createFromForm(): IInventory {
    return {
      ...new Inventory(),
      id: this.editForm.get(['id'])!.value,
      stock: this.editForm.get(['stock'])!.value,
      capcity: this.editForm.get(['capcity'])!.value,
      installedCapcity: this.editForm.get(['installedCapcity'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      inventoryMasterId: this.editForm.get(['inventoryMasterId'])!.value,
      supplierId: this.editForm.get(['supplierId'])!.value,
      hospitalId: this.editForm.get(['hospitalId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventory>>): void {
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
