import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAuditType, AuditType } from 'app/shared/model/audit-type.model';
import { AuditTypeService } from './audit-type.service';

@Component({
  selector: 'tvg-audit-type-update',
  templateUrl: './audit-type-update.component.html',
})
export class AuditTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected auditTypeService: AuditTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditType }) => {
      if (!auditType.id) {
        const today = moment().startOf('day');
        auditType.lastModified = today;
      }

      this.updateForm(auditType);
    });
  }

  updateForm(auditType: IAuditType): void {
    this.editForm.patchValue({
      id: auditType.id,
      name: auditType.name,
      deleted: auditType.deleted,
      lastModified: auditType.lastModified ? auditType.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: auditType.lastModifiedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auditType = this.createFromForm();
    if (auditType.id !== undefined) {
      this.subscribeToSaveResponse(this.auditTypeService.update(auditType));
    } else {
      this.subscribeToSaveResponse(this.auditTypeService.create(auditType));
    }
  }

  private createFromForm(): IAuditType {
    return {
      ...new AuditType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditType>>): void {
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
