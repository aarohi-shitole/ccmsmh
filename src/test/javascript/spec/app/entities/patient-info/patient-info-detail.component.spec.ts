import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { PatientInfoDetailComponent } from 'app/entities/patient-info/patient-info-detail.component';
import { PatientInfo } from 'app/shared/model/patient-info.model';

describe('Component Tests', () => {
  describe('PatientInfo Management Detail Component', () => {
    let comp: PatientInfoDetailComponent;
    let fixture: ComponentFixture<PatientInfoDetailComponent>;
    const route = ({ data: of({ patientInfo: new PatientInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [PatientInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PatientInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PatientInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load patientInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.patientInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
