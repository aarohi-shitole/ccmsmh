import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITaluka, Taluka } from 'app/shared/model/taluka.model';
import { TalukaService } from './taluka.service';
import { IDistrict } from 'app/shared/model/district.model';
import { DistrictService } from 'app/entities/district/district.service';

@Component({
  selector: 'tvg-taluka-update',
  templateUrl: './taluka-update.component.html',
})
export class TalukaUpdateComponent implements OnInit {
  isSaving = false;
  districts: IDistrict[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    deleted: [],
    lgdCode: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    districtId: [],
  });

  constructor(
    protected talukaService: TalukaService,
    protected districtService: DistrictService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taluka }) => {
      if (!taluka.id) {
        const today = moment().startOf('day');
        taluka.lastModified = today;
      }

      this.updateForm(taluka);

      this.districtService.query().subscribe((res: HttpResponse<IDistrict[]>) => (this.districts = res.body || []));
    });
  }

  updateForm(taluka: ITaluka): void {
    this.editForm.patchValue({
      id: taluka.id,
      name: taluka.name,
      deleted: taluka.deleted,
      lgdCode: taluka.lgdCode,
      lastModified: taluka.lastModified ? taluka.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: taluka.lastModifiedBy,
      districtId: taluka.districtId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taluka = this.createFromForm();
    if (taluka.id !== undefined) {
      this.subscribeToSaveResponse(this.talukaService.update(taluka));
    } else {
      this.subscribeToSaveResponse(this.talukaService.create(taluka));
    }
  }

  private createFromForm(): ITaluka {
    return {
      ...new Taluka(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lgdCode: this.editForm.get(['lgdCode'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      districtId: this.editForm.get(['districtId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaluka>>): void {
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
