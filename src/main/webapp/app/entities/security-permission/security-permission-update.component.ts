import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISecurityPermission, SecurityPermission } from 'app/shared/model/security-permission.model';
import { SecurityPermissionService } from './security-permission.service';

@Component({
  selector: 'tvg-security-permission-update',
  templateUrl: './security-permission-update.component.html',
})
export class SecurityPermissionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(
    protected securityPermissionService: SecurityPermissionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ securityPermission }) => {
      if (!securityPermission.id) {
        const today = moment().startOf('day');
        securityPermission.lastModified = today;
      }

      this.updateForm(securityPermission);
    });
  }

  updateForm(securityPermission: ISecurityPermission): void {
    this.editForm.patchValue({
      id: securityPermission.id,
      name: securityPermission.name,
      description: securityPermission.description,
      lastModified: securityPermission.lastModified ? securityPermission.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: securityPermission.lastModifiedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const securityPermission = this.createFromForm();
    if (securityPermission.id !== undefined) {
      this.subscribeToSaveResponse(this.securityPermissionService.update(securityPermission));
    } else {
      this.subscribeToSaveResponse(this.securityPermissionService.create(securityPermission));
    }
  }

  private createFromForm(): ISecurityPermission {
    return {
      ...new SecurityPermission(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecurityPermission>>): void {
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
}
