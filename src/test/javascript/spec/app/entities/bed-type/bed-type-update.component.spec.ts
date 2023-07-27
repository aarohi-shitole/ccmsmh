import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { BedTypeUpdateComponent } from 'app/entities/bed-type/bed-type-update.component';
import { BedTypeService } from 'app/entities/bed-type/bed-type.service';
import { BedType } from 'app/shared/model/bed-type.model';

describe('Component Tests', () => {
  describe('BedType Management Update Component', () => {
    let comp: BedTypeUpdateComponent;
    let fixture: ComponentFixture<BedTypeUpdateComponent>;
    let service: BedTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [BedTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BedTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BedTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BedTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BedType(123);
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
        const entity = new BedType();
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
