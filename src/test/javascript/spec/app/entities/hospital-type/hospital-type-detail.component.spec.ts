import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { HospitalTypeDetailComponent } from 'app/entities/hospital-type/hospital-type-detail.component';
import { HospitalType } from 'app/shared/model/hospital-type.model';

describe('Component Tests', () => {
  describe('HospitalType Management Detail Component', () => {
    let comp: HospitalTypeDetailComponent;
    let fixture: ComponentFixture<HospitalTypeDetailComponent>;
    const route = ({ data: of({ hospitalType: new HospitalType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [HospitalTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(HospitalTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HospitalTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hospitalType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hospitalType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
