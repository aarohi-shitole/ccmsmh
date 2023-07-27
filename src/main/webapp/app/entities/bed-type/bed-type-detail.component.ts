import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBedType } from 'app/shared/model/bed-type.model';

@Component({
  selector: 'tvg-bed-type-detail',
  templateUrl: './bed-type-detail.component.html',
})
export class BedTypeDetailComponent implements OnInit {
  bedType: IBedType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bedType }) => (this.bedType = bedType));
  }

  previousState(): void {
    window.history.back();
  }
}
