import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBedType, BedType } from 'app/shared/model/bed-type.model';
import { BedTypeService } from './bed-type.service';

@Component({
  selector: 'tvg-bed-type-update',
  templateUrl: './bed-type-update.component.html',
})
export class BedTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    perDayOX: [],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected bedTypeService: BedTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bedType }) => {
      if (!bedType.id) {
        const today = moment().startOf('day');
        bedType.lastModified = today;
      }

      this.updateForm(bedType);
    });
  }

  updateForm(bedType: IBedType): void {
    this.editForm.patchValue({
      id: bedType.id,
      name: bedType.name,
      perDayOX: bedType.perDayOX,
      deleted: bedType.deleted,
      lastModified: bedType.lastModified ? bedType.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: bedType.lastModifiedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bedType = this.createFromForm();
    if (bedType.id !== undefined) {
      this.subscribeToSaveResponse(this.bedTypeService.update(bedType));
    } else {
      this.subscribeToSaveResponse(this.bedTypeService.create(bedType));
    }
  }

  private createFromForm(): IBedType {
    return {
      ...new BedType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      perDayOX: this.editForm.get(['perDayOX'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBedType>>): void {
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
