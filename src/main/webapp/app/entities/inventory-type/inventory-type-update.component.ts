import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInventoryType, InventoryType } from 'app/shared/model/inventory-type.model';
import { InventoryTypeService } from './inventory-type.service';

@Component({
  selector: 'tvg-inventory-type-update',
  templateUrl: './inventory-type-update.component.html',
})
export class InventoryTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected inventoryTypeService: InventoryTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryType }) => {
      if (!inventoryType.id) {
        const today = moment().startOf('day');
        inventoryType.lastModified = today;
      }

      this.updateForm(inventoryType);
    });
  }

  updateForm(inventoryType: IInventoryType): void {
    this.editForm.patchValue({
      id: inventoryType.id,
      name: inventoryType.name,
      deleted: inventoryType.deleted,
      lastModified: inventoryType.lastModified ? inventoryType.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: inventoryType.lastModifiedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventoryType = this.createFromForm();
    if (inventoryType.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryTypeService.update(inventoryType));
    } else {
      this.subscribeToSaveResponse(this.inventoryTypeService.create(inventoryType));
    }
  }

  private createFromForm(): IInventoryType {
    return {
      ...new InventoryType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryType>>): void {
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
}
