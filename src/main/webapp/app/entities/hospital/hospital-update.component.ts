import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IHospital, Hospital } from 'app/shared/model/hospital.model';
import { HospitalService } from './hospital.service';
import { IState } from 'app/shared/model/state.model';
import { StateService } from 'app/entities/state/state.service';
import { IDistrict } from 'app/shared/model/district.model';
import { DistrictService } from 'app/entities/district/district.service';
import { ITaluka } from 'app/shared/model/taluka.model';
import { TalukaService } from 'app/entities/taluka/taluka.service';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city/city.service';
import { IMunicipalCorp } from 'app/shared/model/municipal-corp.model';
import { MunicipalCorpService } from 'app/entities/municipal-corp/municipal-corp.service';
import { IHospitalType } from 'app/shared/model/hospital-type.model';
import { HospitalTypeService } from 'app/entities/hospital-type/hospital-type.service';
import { ISupplier } from 'app/shared/model/supplier.model';
import { SupplierService } from 'app/entities/supplier/supplier.service';

type SelectableEntity = IState | IDistrict | ITaluka | ICity | IMunicipalCorp | IHospitalType | ISupplier;

@Component({
  selector: 'tvg-hospital-update',
  templateUrl: './hospital-update.component.html',
})
export class HospitalUpdateComponent implements OnInit {
  isSaving = false;
  states: IState[] = [];
  districts: IDistrict[] = [];
  talukas: ITaluka[] = [];
  cities: ICity[] = [];
  municipalcorps: IMunicipalCorp[] = [];
  hospitaltypes: IHospitalType[] = [];
  suppliers: ISupplier[] = [];

  editForm = this.fb.group({
    id: [],
    category: [null, [Validators.required]],
    subCategory: [null, [Validators.required]],
    contactNo: [],
    latitude: [],
    longitude: [],
    docCount: [],
    email: [],
    name: [null, [Validators.required]],
    registrationNo: [],
    address1: [],
    address2: [],
    area: [],
    pinCode: [null, [Validators.required]],
    hospitalId: [],
    odasFacilityId: [],
    referenceNumber: [],
    statusInd: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    stateId: [],
    districtId: [],
    talukaId: [],
    cityId: [],
    municipalCorpId: [],
    hospitalTypeId: [],
    suppliers: [],
  });

  constructor(
    protected hospitalService: HospitalService,
    protected stateService: StateService,
    protected districtService: DistrictService,
    protected talukaService: TalukaService,
    protected cityService: CityService,
    protected municipalCorpService: MunicipalCorpService,
    protected hospitalTypeService: HospitalTypeService,
    protected supplierService: SupplierService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hospital }) => {
      if (!hospital.id) {
        const today = moment().startOf('day');
        hospital.lastModified = today;
      }

      this.updateForm(hospital);

      this.stateService.query().subscribe((res: HttpResponse<IState[]>) => (this.states = res.body || []));

      this.districtService.query().subscribe((res: HttpResponse<IDistrict[]>) => (this.districts = res.body || []));

      this.talukaService.query().subscribe((res: HttpResponse<ITaluka[]>) => (this.talukas = res.body || []));

      this.cityService.query().subscribe((res: HttpResponse<ICity[]>) => (this.cities = res.body || []));

      this.municipalCorpService.query().subscribe((res: HttpResponse<IMunicipalCorp[]>) => (this.municipalcorps = res.body || []));

      this.hospitalTypeService.query().subscribe((res: HttpResponse<IHospitalType[]>) => (this.hospitaltypes = res.body || []));

      this.supplierService.query().subscribe((res: HttpResponse<ISupplier[]>) => (this.suppliers = res.body || []));
    });
  }

  updateForm(hospital: IHospital): void {
    this.editForm.patchValue({
      id: hospital.id,
      category: hospital.category,
      subCategory: hospital.subCategory,
      contactNo: hospital.contactNo,
      latitude: hospital.latitude,
      longitude: hospital.longitude,
      docCount: hospital.docCount,
      email: hospital.email,
      name: hospital.name,
      registrationNo: hospital.registrationNo,
      address1: hospital.address1,
      address2: hospital.address2,
      area: hospital.area,
      pinCode: hospital.pinCode,
      hospitalId: hospital.hospitalId,
      odasFacilityId: hospital.odasFacilityId,
      referenceNumber: hospital.referenceNumber,
      statusInd: hospital.statusInd,
      lastModified: hospital.lastModified ? hospital.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: hospital.lastModifiedBy,
      stateId: hospital.stateId,
      districtId: hospital.districtId,
      talukaId: hospital.talukaId,
      cityId: hospital.cityId,
      municipalCorpId: hospital.municipalCorpId,
      hospitalTypeId: hospital.hospitalTypeId,
      suppliers: hospital.suppliers,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hospital = this.createFromForm();
    if (hospital.id !== undefined) {
      this.subscribeToSaveResponse(this.hospitalService.update(hospital));
    } else {
      this.subscribeToSaveResponse(this.hospitalService.create(hospital));
    }
  }

  private createFromForm(): IHospital {
    return {
      ...new Hospital(),
      id: this.editForm.get(['id'])!.value,
      category: this.editForm.get(['category'])!.value,
      subCategory: this.editForm.get(['subCategory'])!.value,
      contactNo: this.editForm.get(['contactNo'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      docCount: this.editForm.get(['docCount'])!.value,
      email: this.editForm.get(['email'])!.value,
      name: this.editForm.get(['name'])!.value,
      registrationNo: this.editForm.get(['registrationNo'])!.value,
      address1: this.editForm.get(['address1'])!.value,
      address2: this.editForm.get(['address2'])!.value,
      area: this.editForm.get(['area'])!.value,
      pinCode: this.editForm.get(['pinCode'])!.value,
      hospitalId: this.editForm.get(['hospitalId'])!.value,
      odasFacilityId: this.editForm.get(['odasFacilityId'])!.value,
      referenceNumber: this.editForm.get(['referenceNumber'])!.value,
      statusInd: this.editForm.get(['statusInd'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      stateId: this.editForm.get(['stateId'])!.value,
      districtId: this.editForm.get(['districtId'])!.value,
      talukaId: this.editForm.get(['talukaId'])!.value,
      cityId: this.editForm.get(['cityId'])!.value,
      municipalCorpId: this.editForm.get(['municipalCorpId'])!.value,
      hospitalTypeId: this.editForm.get(['hospitalTypeId'])!.value,
      suppliers: this.editForm.get(['suppliers'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHospital>>): void {
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

  getSelected(selectedVals: ISupplier[], option: ISupplier): ISupplier {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
