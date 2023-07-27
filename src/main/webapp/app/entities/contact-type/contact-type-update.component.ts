import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContactType, ContactType } from 'app/shared/model/contact-type.model';
import { ContactTypeService } from './contact-type.service';

@Component({
  selector: 'tvg-contact-type-update',
  templateUrl: './contact-type-update.component.html',
})
export class ContactTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    deleted: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
  });

  constructor(protected contactTypeService: ContactTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactType }) => {
      if (!contactType.id) {
        const today = moment().startOf('day');
        contactType.lastModified = today;
      }

      this.updateForm(contactType);
    });
  }

  updateForm(contactType: IContactType): void {
    this.editForm.patchValue({
      id: contactType.id,
      name: contactType.name,
      deleted: contactType.deleted,
      lastModified: contactType.lastModified ? contactType.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: contactType.lastModifiedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactType = this.createFromForm();
    if (contactType.id !== undefined) {
      this.subscribeToSaveResponse(this.contactTypeService.update(contactType));
    } else {
      this.subscribeToSaveResponse(this.contactTypeService.create(contactType));
    }
  }

  private createFromForm(): IContactType {
    return {
      ...new ContactType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      deleted: this.editForm.get(['deleted'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactType>>): void {
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
