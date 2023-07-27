import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISecurityUser, SecurityUser } from 'app/shared/model/security-user.model';
import { SecurityUserService } from './security-user.service';
import { ISecurityPermission } from 'app/shared/model/security-permission.model';
import { SecurityPermissionService } from 'app/entities/security-permission/security-permission.service';
import { ISecurityRole } from 'app/shared/model/security-role.model';
import { SecurityRoleService } from 'app/entities/security-role/security-role.service';

type SelectableEntity = ISecurityPermission | ISecurityRole;

@Component({
  selector: 'tvg-security-user-update',
  templateUrl: './security-user-update.component.html',
})
export class SecurityUserUpdateComponent implements OnInit {
  isSaving = false;
  securitypermissions: ISecurityPermission[] = [];
  securityroles: ISecurityRole[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    designation: [],
    login: [null, [Validators.required]],
    passwordHash: [null, [Validators.required]],
    email: [null, []],
    imageUrl: [],
    activated: [null, [Validators.required]],
    langKey: [],
    activationKey: [],
    resetKey: [],
    resetDate: [],
    mobileNo: [],
    oneTimePassword: [],
    otpExpiryTime: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    securityPermissions: [],
    securityRoles: [],
  });

  constructor(
    protected securityUserService: SecurityUserService,
    protected securityPermissionService: SecurityPermissionService,
    protected securityRoleService: SecurityRoleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityUser }) => {
      if (!securityUser.id) {
        const today = moment().startOf('day');
        securityUser.resetDate = today;
        securityUser.otpExpiryTime = today;
        securityUser.lastModified = today;
      }

      this.updateForm(securityUser);

      this.securityPermissionService
        .query()
        .subscribe((res: HttpResponse<ISecurityPermission[]>) => (this.securitypermissions = res.body || []));

      this.securityRoleService.query().subscribe((res: HttpResponse<ISecurityRole[]>) => (this.securityroles = res.body || []));
    });
  }

  updateForm(securityUser: ISecurityUser): void {
    this.editForm.patchValue({
      id: securityUser.id,
      firstName: securityUser.firstName,
      lastName: securityUser.lastName,
      designation: securityUser.designation,
      login: securityUser.login,
      passwordHash: securityUser.passwordHash,
      email: securityUser.email,
      imageUrl: securityUser.imageUrl,
      activated: securityUser.activated,
      langKey: securityUser.langKey,
      activationKey: securityUser.activationKey,
      resetKey: securityUser.resetKey,
      resetDate: securityUser.resetDate ? securityUser.resetDate.format(DATE_TIME_FORMAT) : null,
      mobileNo: securityUser.mobileNo,
      oneTimePassword: securityUser.oneTimePassword,
      otpExpiryTime: securityUser.otpExpiryTime ? securityUser.otpExpiryTime.format(DATE_TIME_FORMAT) : null,
      lastModified: securityUser.lastModified ? securityUser.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: securityUser.lastModifiedBy,
      securityPermissions: securityUser.securityPermissions,
      securityRoles: securityUser.securityRoles,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securityUser = this.createFromForm();
    if (securityUser.id !== undefined) {
      this.subscribeToSaveResponse(this.securityUserService.update(securityUser));
    } else {
      this.subscribeToSaveResponse(this.securityUserService.create(securityUser));
    }
  }

  private createFromForm(): ISecurityUser {
    return {
      ...new SecurityUser(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      login: this.editForm.get(['login'])!.value,
      passwordHash: this.editForm.get(['passwordHash'])!.value,
      email: this.editForm.get(['email'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      activated: this.editForm.get(['activated'])!.value,
      langKey: this.editForm.get(['langKey'])!.value,
      activationKey: this.editForm.get(['activationKey'])!.value,
      resetKey: this.editForm.get(['resetKey'])!.value,
      resetDate: this.editForm.get(['resetDate'])!.value ? moment(this.editForm.get(['resetDate'])!.value, DATE_TIME_FORMAT) : undefined,
      mobileNo: this.editForm.get(['mobileNo'])!.value,
      oneTimePassword: this.editForm.get(['oneTimePassword'])!.value,
      otpExpiryTime: this.editForm.get(['otpExpiryTime'])!.value
        ? moment(this.editForm.get(['otpExpiryTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      securityPermissions: this.editForm.get(['securityPermissions'])!.value,
      securityRoles: this.editForm.get(['securityRoles'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecurityUser>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
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
