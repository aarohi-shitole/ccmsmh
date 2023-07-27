import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBedTransactions } from 'app/shared/model/bed-transactions.model';

@Component({
  selector: 'tvg-bed-transactions-detail',
  templateUrl: './bed-transactions-detail.component.html',
})
export class BedTransactionsDetailComponent implements OnInit {
  bedTransactions: IBedTransactions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bedTransactions }) => (this.bedTransactions = bedTransactions));
  }

  previousState(): void {
    window.history.back();
  }
}
