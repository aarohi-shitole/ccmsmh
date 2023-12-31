import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHospitalType } from 'app/shared/model/hospital-type.model';

@Component({
  selector: 'tvg-hospital-type-detail',
  templateUrl: './hospital-type-detail.component.html',
})
export class HospitalTypeDetailComponent implements OnInit {
  hospitalType: IHospitalType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hospitalType }) => (this.hospitalType = hospitalType));
  }

  previousState(): void {
    window.history.back();
  }
}
