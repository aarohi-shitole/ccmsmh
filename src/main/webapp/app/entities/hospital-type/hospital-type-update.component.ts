import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IHospitalType, HospitalType } from 'app/shared/model/hospital-type.model';
import { HospitalTypeService } from './hospital-type.service';

@Component({
  selector: 'tvg-hospital-type-update',
  templateUrl: './hospital-type-update.component.html',
})
export class HospitalTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    desciption: [],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected hospitalTypeService: HospitalTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hospitalType }) => {
      if (!hospitalType.id) {
        const today = moment().startOf('day');
        hospitalType.lastModified = today;
      }

      this.updateForm(hospitalType);
    });
  }

  updateForm(hospitalType: IHospitalType): void {
    this.editForm.patchValue({
      id: hospitalType.id,
      name: hospitalType.name,
      desciption: hospitalType.desciption,
      deleted: hospitalType.deleted,
      lastModified: hospitalType.lastModified ? hospitalType.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: hospitalType.lastModifiedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hospitalType = this.createFromForm();
    if (hospitalType.id !== undefined) {
      this.subscribeToSaveResponse(this.hospitalTypeService.update(hospitalType));
    } else {
      this.subscribeToSaveResponse(this.hospitalTypeService.create(hospitalType));
    }
  }

  private createFromForm(): IHospitalType {
    return {
      ...new HospitalType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      desciption: this.editForm.get(['desciption'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHospitalType>>): void {
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
