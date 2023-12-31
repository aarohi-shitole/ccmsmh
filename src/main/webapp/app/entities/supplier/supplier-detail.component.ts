import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplier } from 'app/shared/model/supplier.model';

@Component({
  selector: 'tvg-supplier-detail',
  templateUrl: './supplier-detail.component.html',
})
export class SupplierDetailComponent implements OnInit {
  supplier: ISupplier | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supplier }) => (this.supplier = supplier));
  }

  previousState(): void {
    window.history.back();
  }
}
