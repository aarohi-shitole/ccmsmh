import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInventoryUsed, InventoryUsed } from 'app/shared/model/inventory-used.model';
import { InventoryUsedService } from './inventory-used.service';
import { IInventory } from 'app/shared/model/inventory.model';
import { InventoryService } from 'app/entities/inventory/inventory.service';

@Component({
  selector: 'tvg-inventory-used-update',
  templateUrl: './inventory-used-update.component.html',
})
export class InventoryUsedUpdateComponent implements OnInit {
  isSaving = false;
  inventories: IInventory[] = [];

  editForm = this.fb.group({
    id: [],
    stock: [],
    capcity: [],
    comment: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    inventoryId: [],
  });

  constructor(
    protected inventoryUsedService: InventoryUsedService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryUsed }) => {
      if (!inventoryUsed.id) {
        const today = moment().startOf('day');
        inventoryUsed.lastModified = today;
      }

      this.updateForm(inventoryUsed);

      this.inventoryService.query().subscribe((res: HttpResponse<IInventory[]>) => (this.inventories = res.body || []));
    });
  }

  updateForm(inventoryUsed: IInventoryUsed): void {
    this.editForm.patchValue({
      id: inventoryUsed.id,
      stock: inventoryUsed.stock,
      capcity: inventoryUsed.capcity,
      comment: inventoryUsed.comment,
      lastModified: inventoryUsed.lastModified ? inventoryUsed.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: inventoryUsed.lastModifiedBy,
      inventoryId: inventoryUsed.inventoryId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventoryUsed = this.createFromForm();
    if (inventoryUsed.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryUsedService.update(inventoryUsed));
    } else {
      this.subscribeToSaveResponse(this.inventoryUsedService.create(inventoryUsed));
    }
  }

  private createFromForm(): IInventoryUsed {
    return {
      ...new InventoryUsed(),
      id: this.editForm.get(['id'])!.value,
      stock: this.editForm.get(['stock'])!.value,
      capcity: this.editForm.get(['capcity'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      inventoryId: this.editForm.get(['inventoryId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryUsed>>): void {
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

  trackById(index: number, item: IInventory): any {
    return item.id;
  }
}
