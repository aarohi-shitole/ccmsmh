import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryType } from 'app/shared/model/inventory-type.model';

@Component({
  selector: 'tvg-inventory-type-detail',
  templateUrl: './inventory-type-detail.component.html',
})
export class InventoryTypeDetailComponent implements OnInit {
  inventoryType: IInventoryType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryType }) => (this.inventoryType = inventoryType));
  }

  previousState(): void {
    window.history.back();
  }
}
