import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBedInventory } from 'app/shared/model/bed-inventory.model';

@Component({
  selector: 'tvg-bed-inventory-detail',
  templateUrl: './bed-inventory-detail.component.html',
})
export class BedInventoryDetailComponent implements OnInit {
  bedInventory: IBedInventory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bedInventory }) => (this.bedInventory = bedInventory));
  }

  previousState(): void {
    window.history.back();
  }
}
