import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISecurityPermission } from 'app/shared/model/security-permission.model';

@Component({
  selector: 'tvg-security-permission-detail',
  templateUrl: './security-permission-detail.component.html',
})
export class SecurityPermissionDetailComponent implements OnInit {
  securityPermission: ISecurityPermission | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityPermission }) => (this.securityPermission = securityPermission));
  }

  previousState(): void {
    window.history.back();
  }
}
