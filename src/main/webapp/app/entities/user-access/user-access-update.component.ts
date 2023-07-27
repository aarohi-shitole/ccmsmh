import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserAccess, UserAccess } from 'app/shared/model/user-access.model';
import { UserAccessService } from './user-access.service';
import { ISecurityUser } from 'app/shared/model/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/security-user.service';
import { TalukaService } from 'app/entities/taluka/taluka.service';
import { StateService } from 'app/entities/state/state.service';
import { MunicipalCorpService } from 'app/entities/municipal-corp/municipal-corp.service';
import { HospitalService } from 'app/entities/hospital/hospital.service';
import { SupplierService } from 'app/entities/supplier/supplier.service';
import { DistrictService } from 'app/entities/district/district.service';

@Component({
  selector: 'tvg-user-access-update',
  templateUrl: './user-access-update.component.html',
})
export class UserAccessUpdateComponent implements OnInit {
  isSaving = false;
  securityusers: ISecurityUser[] = [];
  accessLOV: any[] = [];

  editForm = this.fb.group({
    id: [],
    level: [null, [Validators.required]],
    accessId: [null, [Validators.required]],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    securityUserId: [null, [Validators.required]],
  });

  constructor(
    protected userAccessService: UserAccessService,
    protected securityUserService: SecurityUserService,
    protected talukaService: TalukaService,
    protected stateService: StateService,
    protected municipalCorpService: MunicipalCorpService,
    protected hospitalService: HospitalService,
    protected supplierService: SupplierService,
    protected districtService: DistrictService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userAccess }) => {
      if (!userAccess.id) {
        const today = moment().startOf('day');
        userAccess.lastModified = today;
      }

      this.updateForm(userAccess);

      this.securityUserService
        .query({ size: 10000, page: 0 })
        .subscribe((res: HttpResponse<ISecurityUser[]>) => (this.securityusers = res.body || []));
    });
  }

  updateForm(userAccess: IUserAccess): void {
    this.editForm.patchValue({
      id: userAccess.id,
      level: userAccess.level,
      accessId: userAccess.accessId,
      lastModified: userAccess.lastModified ? userAccess.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: userAccess.lastModifiedBy,
      securityUserId: userAccess.securityUserId,
    });
    if (userAccess.level) {
      this.onChange(userAccess.level);
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userAccess = this.createFromForm();
    if (userAccess.id !== undefined) {
      this.subscribeToSaveResponse(this.userAccessService.update(userAccess));
    } else {
      this.subscribeToSaveResponse(this.userAccessService.create(userAccess));
    }
  }

  private createFromForm(): IUserAccess {
    return {
      ...new UserAccess(),
      id: this.editForm.get(['id'])!.value,
      level: this.editForm.get(['level'])!.value,
      accessId: this.editForm.get(['accessId'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      securityUserId: this.editForm.get(['securityUserId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserAccess>>): void {
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

  trackById(index: number, item: ISecurityUser): any {
    return item.id;
  }

  onChange(value: string): void {
    if (value === 'HOSPITAL') {
      this.hospitalService.query({ size: 4000, page: 0 }).subscribe((res: HttpResponse<any[]>) => (this.accessLOV = res.body || []));
    }
    if (value === 'SUPPLIER') {
      this.supplierService.query().subscribe((res: HttpResponse<any[]>) => (this.accessLOV = res.body || []));
    }
    if (value === 'TALUKA') {
      this.talukaService.query().subscribe((res: HttpResponse<any[]>) => (this.accessLOV = res.body || []));
    }
    if (value === 'DISTRICT') {
      this.districtService.query().subscribe((res: HttpResponse<any[]>) => (this.accessLOV = res.body || []));
    }
    if (value === 'STATE') {
      this.stateService.query().subscribe((res: HttpResponse<any[]>) => (this.accessLOV = res.body || []));
    }
    if (value === 'MUNCIPALCORP') {
      this.municipalCorpService.query().subscribe((res: HttpResponse<any[]>) => (this.accessLOV = res.body || []));
    }
  }
}
