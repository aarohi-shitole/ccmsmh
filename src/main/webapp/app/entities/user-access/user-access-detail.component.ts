import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserAccess } from 'app/shared/model/user-access.model';

@Component({
  selector: 'tvg-user-access-detail',
  templateUrl: './user-access-detail.component.html',
})
export class UserAccessDetailComponent implements OnInit {
  userAccess: IUserAccess | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userAccess }) => (this.userAccess = userAccess));
  }

  previousState(): void {
    window.history.back();
  }
}
