import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISupplier, Supplier } from 'app/shared/model/supplier.model';
import { SupplierService } from './supplier.service';
import { IState } from 'app/shared/model/state.model';
import { StateService } from 'app/entities/state/state.service';
import { IDistrict } from 'app/shared/model/district.model';
import { DistrictService } from 'app/entities/district/district.service';
import { ITaluka } from 'app/shared/model/taluka.model';
import { TalukaService } from 'app/entities/taluka/taluka.service';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city/city.service';
import { IInventoryType } from 'app/shared/model/inventory-type.model';
import { InventoryTypeService } from 'app/entities/inventory-type/inventory-type.service';

type SelectableEntity = IState | IDistrict | ITaluka | ICity | IInventoryType;

@Component({
  selector: 'tvg-supplier-update',
  templateUrl: './supplier-update.component.html',
})
export class SupplierUpdateComponent implements OnInit {
  isSaving = false;
  states: IState[] = [];
  districts: IDistrict[] = [];
  talukas: ITaluka[] = [];
  cities: ICity[] = [];
  inventorytypes: IInventoryType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    supplierType: [null, [Validators.required]],
    contactNo: [],
    latitude: [],
    longitude: [],
    email: [],
    registrationNo: [],
    address1: [],
    address2: [],
    area: [],
    pinCode: [null, [Validators.required]],
    statusInd: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    stateId: [],
    districtId: [],
    talukaId: [],
    cityId: [],
    inventoryTypeId: [],
  });

  constructor(
    protected supplierService: SupplierService,
    protected stateService: StateService,
    protected districtService: DistrictService,
    protected talukaService: TalukaService,
    protected cityService: CityService,
    protected inventoryTypeService: InventoryTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supplier }) => {
      if (!supplier.id) {
        const today = moment().startOf('day');
        supplier.lastModified = today;
      }

      this.updateForm(supplier);

      this.stateService.query().subscribe((res: HttpResponse<IState[]>) => (this.states = res.body || []));

      this.districtService.query().subscribe((res: HttpResponse<IDistrict[]>) => (this.districts = res.body || []));

      this.talukaService.query().subscribe((res: HttpResponse<ITaluka[]>) => (this.talukas = res.body || []));

      this.cityService.query().subscribe((res: HttpResponse<ICity[]>) => (this.cities = res.body || []));

      this.inventoryTypeService.query().subscribe((res: HttpResponse<IInventoryType[]>) => (this.inventorytypes = res.body || []));
    });
  }

  updateForm(supplier: ISupplier): void {
    this.editForm.patchValue({
      id: supplier.id,
      name: supplier.name,
      supplierType: supplier.supplierType,
      contactNo: supplier.contactNo,
      latitude: supplier.latitude,
      longitude: supplier.longitude,
      email: supplier.email,
      registrationNo: supplier.registrationNo,
      address1: supplier.address1,
      address2: supplier.address2,
      area: supplier.area,
      pinCode: supplier.pinCode,
      statusInd: supplier.statusInd,
      lastModified: supplier.lastModified ? supplier.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: supplier.lastModifiedBy,
      stateId: supplier.stateId,
      districtId: supplier.districtId,
      talukaId: supplier.talukaId,
      cityId: supplier.cityId,
      inventoryTypeId: supplier.inventoryTypeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const supplier = this.createFromForm();
    if (supplier.id !== undefined) {
      this.subscribeToSaveResponse(this.supplierService.update(supplier));
    } else {
      this.subscribeToSaveResponse(this.supplierService.create(supplier));
    }
  }

  private createFromForm(): ISupplier {
    return {
      ...new Supplier(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      supplierType: this.editForm.get(['supplierType'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      email: this.editForm.get(['email'])!.value,
      registrationNo: this.editForm.get(['registrationNo'])!.value,
      address1: this.editForm.get(['address1'])!.value,
      address2: this.editForm.get(['address2'])!.value,
      area: this.editForm.get(['area'])!.value,
      pinCode: this.editForm.get(['pinCode'])!.value,
      statusInd: this.editForm.get(['statusInd'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      stateId: this.editForm.get(['stateId'])!.value,
      districtId: this.editForm.get(['districtId'])!.value,
      talukaId: this.editForm.get(['talukaId'])!.value,
      cityId: this.editForm.get(['cityId'])!.value,
      inventoryTypeId: this.editForm.get(['inventoryTypeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplier>>): void {
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
