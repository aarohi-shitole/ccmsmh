import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { PatientInfoUpdateComponent } from 'app/entities/patient-info/patient-info-update.component';
import { PatientInfoService } from 'app/entities/patient-info/patient-info.service';
import { PatientInfo } from 'app/shared/model/patient-info.model';

describe('Component Tests', () => {
  describe('PatientInfo Management Update Component', () => {
    let comp: PatientInfoUpdateComponent;
    let fixture: ComponentFixture<PatientInfoUpdateComponent>;
    let service: PatientInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [PatientInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PatientInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PatientInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PatientInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientInfo(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PatientInfo();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
