import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaluka } from 'app/shared/model/taluka.model';

@Component({
  selector: 'tvg-taluka-detail',
  templateUrl: './taluka-detail.component.html',
})
export class TalukaDetailComponent implements OnInit {
  taluka: ITaluka | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taluka }) => (this.taluka = taluka));
  }

  previousState(): void {
    window.history.back();
  }
}
