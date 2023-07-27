import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { HospitalTypeUpdateComponent } from 'app/entities/hospital-type/hospital-type-update.component';
import { HospitalTypeService } from 'app/entities/hospital-type/hospital-type.service';
import { HospitalType } from 'app/shared/model/hospital-type.model';

describe('Component Tests', () => {
  describe('HospitalType Management Update Component', () => {
    let comp: HospitalTypeUpdateComponent;
    let fixture: ComponentFixture<HospitalTypeUpdateComponent>;
    let service: HospitalTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [HospitalTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(HospitalTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HospitalTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HospitalTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HospitalType(123);
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
        const entity = new HospitalType();
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
