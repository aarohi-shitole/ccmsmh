import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMunicipalCorp, MunicipalCorp } from 'app/shared/model/municipal-corp.model';
import { MunicipalCorpService } from './municipal-corp.service';
import { IDistrict } from 'app/shared/model/district.model';
import { DistrictService } from 'app/entities/district/district.service';

@Component({
  selector: 'tvg-municipal-corp-update',
  templateUrl: './municipal-corp-update.component.html',
})
export class MunicipalCorpUpdateComponent implements OnInit {
  isSaving = false;
  districts: IDistrict[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    districtId: [],
  });

  constructor(
    protected municipalCorpService: MunicipalCorpService,
    protected districtService: DistrictService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ municipalCorp }) => {
      if (!municipalCorp.id) {
        const today = moment().startOf('day');
        municipalCorp.lastModified = today;
      }

      this.updateForm(municipalCorp);

      this.districtService.query().subscribe((res: HttpResponse<IDistrict[]>) => (this.districts = res.body || []));
    });
  }

  updateForm(municipalCorp: IMunicipalCorp): void {
    this.editForm.patchValue({
      id: municipalCorp.id,
      name: municipalCorp.name,
      deleted: municipalCorp.deleted,
      lastModified: municipalCorp.lastModified ? municipalCorp.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: municipalCorp.lastModifiedBy,
      districtId: municipalCorp.districtId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const municipalCorp = this.createFromForm();
    if (municipalCorp.id !== undefined) {
      this.subscribeToSaveResponse(this.municipalCorpService.update(municipalCorp));
    } else {
      this.subscribeToSaveResponse(this.municipalCorpService.create(municipalCorp));
    }
  }

  private createFromForm(): IMunicipalCorp {
    return {
      ...new MunicipalCorp(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      districtId: this.editForm.get(['districtId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMunicipalCorp>>): void {
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

  trackById(index: number, item: IDistrict): any {
    return item.id;
  }
}
