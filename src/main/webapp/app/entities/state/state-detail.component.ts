import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IState } from 'app/shared/model/state.model';

@Component({
  selector: 'tvg-state-detail',
  templateUrl: './state-detail.component.html',
})
export class StateDetailComponent implements OnInit {
  state: IState | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ state }) => (this.state = state));
  }

  previousState(): void {
    window.history.back();
  }
}
