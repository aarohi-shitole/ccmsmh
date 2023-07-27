import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAuditSystem, AuditSystem } from 'app/shared/model/audit-system.model';
import { AuditSystemService } from './audit-system.service';
import { IHospital } from 'app/shared/model/hospital.model';
import { HospitalService } from 'app/entities/hospital/hospital.service';
import { ISupplier } from 'app/shared/model/supplier.model';
import { SupplierService } from 'app/entities/supplier/supplier.service';
import { IAuditType } from 'app/shared/model/audit-type.model';
import { AuditTypeService } from 'app/entities/audit-type/audit-type.service';

type SelectableEntity = IHospital | ISupplier | IAuditType;

@Component({
  selector: 'tvg-audit-system-update',
  templateUrl: './audit-system-update.component.html',
})
export class AuditSystemUpdateComponent implements OnInit {
  isSaving = false;
  hospitals: IHospital[] = [];
  suppliers: ISupplier[] = [];
  audittypes: IAuditType[] = [];

  editForm = this.fb.group({
    id: [],
    auditorName: [null, [Validators.required]],
    defectCount: [],
    defectFixCount: [],
    inspectionDate: [null, [Validators.required]],
    remark: [],
    status: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    hospitalId: [],
    supplierId: [],
    auditTypeId: [],
  });

  constructor(
    protected auditSystemService: AuditSystemService,
    protected hospitalService: HospitalService,
    protected supplierService: SupplierService,
    protected auditTypeService: AuditTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditSystem }) => {
      if (!auditSystem.id) {
        const today = moment().startOf('day');
        auditSystem.inspectionDate = today;
        auditSystem.lastModified = today;
      }

      this.updateForm(auditSystem);

      this.hospitalService.query().subscribe((res: HttpResponse<IHospital[]>) => (this.hospitals = res.body || []));

      this.supplierService.query().subscribe((res: HttpResponse<ISupplier[]>) => (this.suppliers = res.body || []));

      this.auditTypeService.query().subscribe((res: HttpResponse<IAuditType[]>) => (this.audittypes = res.body || []));
    });
  }

  updateForm(auditSystem: IAuditSystem): void {
    this.editForm.patchValue({
      id: auditSystem.id,
      auditorName: auditSystem.auditorName,
      defectCount: auditSystem.defectCount,
      defectFixCount: auditSystem.defectFixCount,
      inspectionDate: auditSystem.inspectionDate ? auditSystem.inspectionDate.format(DATE_TIME_FORMAT) : null,
      remark: auditSystem.remark,
      status: auditSystem.status,
      lastModified: auditSystem.lastModified ? auditSystem.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: auditSystem.lastModifiedBy,
      hospitalId: auditSystem.hospitalId,
      supplierId: auditSystem.supplierId,
      auditTypeId: auditSystem.auditTypeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auditSystem = this.createFromForm();
    if (auditSystem.id !== undefined) {
      this.subscribeToSaveResponse(this.auditSystemService.update(auditSystem));
    } else {
      this.subscribeToSaveResponse(this.auditSystemService.create(auditSystem));
    }
  }

  private createFromForm(): IAuditSystem {
    return {
      ...new AuditSystem(),
      id: this.editForm.get(['id'])!.value,
      auditorName: this.editForm.get(['auditorName'])!.value,
      defectCount: this.editForm.get(['defectCount'])!.value,
      defectFixCount: this.editForm.get(['defectFixCount'])!.value,
      inspectionDate: this.editForm.get(['inspectionDate'])!.value
        ? moment(this.editForm.get(['inspectionDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      remark: this.editForm.get(['remark'])!.value,
      status: this.editForm.get(['status'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      hospitalId: this.editForm.get(['hospitalId'])!.value,
      supplierId: this.editForm.get(['supplierId'])!.value,
      auditTypeId: this.editForm.get(['auditTypeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditSystem>>): void {
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
}
