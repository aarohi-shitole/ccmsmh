import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISecurityRole, SecurityRole } from 'app/shared/model/security-role.model';
import { SecurityRoleService } from './security-role.service';
import { ISecurityPermission } from 'app/shared/model/security-permission.model';
import { SecurityPermissionService } from 'app/entities/security-permission/security-permission.service';

@Component({
  selector: 'tvg-security-role-update',
  templateUrl: './security-role-update.component.html',
})
export class SecurityRoleUpdateComponent implements OnInit {
  isSaving = false;
  securitypermissions: ISecurityPermission[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    securityPermissions: [],
  });

  constructor(
    protected securityRoleService: SecurityRoleService,
    protected securityPermissionService: SecurityPermissionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityRole }) => {
      if (!securityRole.id) {
        const today = moment().startOf('day');
        securityRole.lastModified = today;
      }

      this.updateForm(securityRole);

      this.securityPermissionService
        .query()
        .subscribe((res: HttpResponse<ISecurityPermission[]>) => (this.securitypermissions = res.body || []));
    });
  }

  updateForm(securityRole: ISecurityRole): void {
    this.editForm.patchValue({
      id: securityRole.id,
      name: securityRole.name,
      description: securityRole.description,
      lastModified: securityRole.lastModified ? securityRole.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: securityRole.lastModifiedBy,
      securityPermissions: securityRole.securityPermissions,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securityRole = this.createFromForm();
    if (securityRole.id !== undefined) {
      this.subscribeToSaveResponse(this.securityRoleService.update(securityRole));
    } else {
      this.subscribeToSaveResponse(this.securityRoleService.create(securityRole));
    }
  }

  private createFromForm(): ISecurityRole {
    return {
      ...new SecurityRole(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      securityPermissions: this.editForm.get(['securityPermissions'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecurityRole>>): void {
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

  trackById(index: number, item: ISecurityPermission): any {
    return item.id;
  }

  getSelected(selectedVals: ISecurityPermission[], option: ISecurityPermission): ISecurityPermission {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
