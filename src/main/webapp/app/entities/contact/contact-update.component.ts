import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContact, Contact } from 'app/shared/model/contact.model';
import { ContactService } from './contact.service';
import { IContactType } from 'app/shared/model/contact-type.model';
import { ContactTypeService } from 'app/entities/contact-type/contact-type.service';
import { IHospital } from 'app/shared/model/hospital.model';
import { HospitalService } from 'app/entities/hospital/hospital.service';
import { ISupplier } from 'app/shared/model/supplier.model';
import { SupplierService } from 'app/entities/supplier/supplier.service';

type SelectableEntity = IContactType | IHospital | ISupplier;

@Component({
  selector: 'tvg-contact-update',
  templateUrl: './contact-update.component.html',
})
export class ContactUpdateComponent implements OnInit {
  isSaving = false;
  contacttypes: IContactType[] = [];
  hospitals: IHospital[] = [];
  suppliers: ISupplier[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    contactNo: [],
    email: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    contactTypeId: [],
    hospitalId: [],
    supplierId: [],
  });

  constructor(
    protected contactService: ContactService,
    protected contactTypeService: ContactTypeService,
    protected hospitalService: HospitalService,
    protected supplierService: SupplierService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contact }) => {
      if (!contact.id) {
        const today = moment().startOf('day');
        contact.lastModified = today;
      }

      this.updateForm(contact);

      this.contactTypeService.query().subscribe((res: HttpResponse<IContactType[]>) => (this.contacttypes = res.body || []));

      this.hospitalService.query().subscribe((res: HttpResponse<IHospital[]>) => (this.hospitals = res.body || []));

      this.supplierService.query().subscribe((res: HttpResponse<ISupplier[]>) => (this.suppliers = res.body || []));
    });
  }

  updateForm(contact: IContact): void {
    this.editForm.patchValue({
      id: contact.id,
      name: contact.name,
      contactNo: contact.contactNo,
      email: contact.email,
      lastModified: contact.lastModified ? contact.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: contact.lastModifiedBy,
      contactTypeId: contact.contactTypeId,
      hospitalId: contact.hospitalId,
      supplierId: contact.supplierId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contact = this.createFromForm();
    if (contact.id !== undefined) {
      this.subscribeToSaveResponse(this.contactService.update(contact));
    } else {
      this.subscribeToSaveResponse(this.contactService.create(contact));
    }
  }

  private createFromForm(): IContact {
    return {
      ...new Contact(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      email: this.editForm.get(['email'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      contactTypeId: this.editForm.get(['contactTypeId'])!.value,
      hospitalId: this.editForm.get(['hospitalId'])!.value,
      supplierId: this.editForm.get(['supplierId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContact>>): void {
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
