import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITransactions, Transactions } from 'app/shared/model/transactions.model';
import { TransactionsService } from './transactions.service';
import { ISupplier } from 'app/shared/model/supplier.model';
import { SupplierService } from 'app/entities/supplier/supplier.service';
import { IInventory } from 'app/shared/model/inventory.model';
import { InventoryService } from 'app/entities/inventory/inventory.service';

type SelectableEntity = ISupplier | IInventory;

@Component({
  selector: 'tvg-transactions-update',
  templateUrl: './transactions-update.component.html',
})
export class TransactionsUpdateComponent implements OnInit {
  isSaving = false;
  suppliers: ISupplier[] = [];
  inventories: IInventory[] = [];

  editForm = this.fb.group({
    id: [],
    stockReq: [null, [Validators.required]],
    stockProvided: [],
    status: [null, [Validators.required]],
    comment: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    supplierId: [],
    inventoryId: [],
  });

  constructor(
    protected transactionsService: TransactionsService,
    protected supplierService: SupplierService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactions }) => {
      if (!transactions.id) {
        const today = moment().startOf('day');
        transactions.lastModified = today;
      }

      this.updateForm(transactions);

      this.supplierService.query().subscribe((res: HttpResponse<ISupplier[]>) => (this.suppliers = res.body || []));

      this.inventoryService.query().subscribe((res: HttpResponse<IInventory[]>) => (this.inventories = res.body || []));
    });
  }

  updateForm(transactions: ITransactions): void {
    this.editForm.patchValue({
      id: transactions.id,
      stockReq: transactions.stockReq,
      stockProvided: transactions.stockProvided,
      status: transactions.status,
      comment: transactions.comment,
      lastModified: transactions.lastModified ? transactions.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: transactions.lastModifiedBy,
      supplierId: transactions.supplierId,
      inventoryId: transactions.inventoryId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactions = this.createFromForm();
    if (transactions.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionsService.update(transactions));
    } else {
      this.subscribeToSaveResponse(this.transactionsService.create(transactions));
    }
  }

  private createFromForm(): ITransactions {
    return {
      ...new Transactions(),
      id: this.editForm.get(['id'])!.value,
      stockReq: this.editForm.get(['stockReq'])!.value,
      stockProvided: this.editForm.get(['stockProvided'])!.value,
      status: this.editForm.get(['status'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      supplierId: this.editForm.get(['supplierId'])!.value,
      inventoryId: this.editForm.get(['inventoryId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactions>>): void {
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
