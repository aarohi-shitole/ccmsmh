import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatientInfo } from 'app/shared/model/patient-info.model';

@Component({
  selector: 'tvg-patient-info-detail',
  templateUrl: './patient-info-detail.component.html',
})
export class PatientInfoDetailComponent implements OnInit {
  patientInfo: IPatientInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patientInfo }) => (this.patientInfo = patientInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
