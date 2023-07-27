import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDistrict, District } from 'app/shared/model/district.model';
import { DistrictService } from './district.service';
import { IState } from 'app/shared/model/state.model';
import { StateService } from 'app/entities/state/state.service';
import { IDivision } from 'app/shared/model/division.model';
import { DivisionService } from 'app/entities/division/division.service';

type SelectableEntity = IState | IDivision;

@Component({
  selector: 'tvg-district-update',
  templateUrl: './district-update.component.html',
})
export class DistrictUpdateComponent implements OnInit {
  isSaving = false;
  states: IState[] = [];
  divisions: IDivision[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    deleted: [],
    lgdCode: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    stateId: [],
    divisionId: [],
  });

  constructor(
    protected districtService: DistrictService,
    protected stateService: StateService,
    protected divisionService: DivisionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ district }) => {
      if (!district.id) {
        const today = moment().startOf('day');
        district.lastModified = today;
      }

      this.updateForm(district);

      this.stateService.query().subscribe((res: HttpResponse<IState[]>) => (this.states = res.body || []));

      this.divisionService.query().subscribe((res: HttpResponse<IDivision[]>) => (this.divisions = res.body || []));
    });
  }

  updateForm(district: IDistrict): void {
    this.editForm.patchValue({
      id: district.id,
      name: district.name,
      deleted: district.deleted,
      lgdCode: district.lgdCode,
      lastModified: district.lastModified ? district.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: district.lastModifiedBy,
      stateId: district.stateId,
      divisionId: district.divisionId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const district = this.createFromForm();
    if (district.id !== undefined) {
      this.subscribeToSaveResponse(this.districtService.update(district));
    } else {
      this.subscribeToSaveResponse(this.districtService.create(district));
    }
  }

  private createFromForm(): IDistrict {
    return {
      ...new District(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lgdCode: this.editForm.get(['lgdCode'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      stateId: this.editForm.get(['stateId'])!.value,
      divisionId: this.editForm.get(['divisionId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistrict>>): void {
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
