import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { TalukaUpdateComponent } from 'app/entities/taluka/taluka-update.component';
import { TalukaService } from 'app/entities/taluka/taluka.service';
import { Taluka } from 'app/shared/model/taluka.model';

describe('Component Tests', () => {
  describe('Taluka Management Update Component', () => {
    let comp: TalukaUpdateComponent;
    let fixture: ComponentFixture<TalukaUpdateComponent>;
    let service: TalukaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [TalukaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TalukaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TalukaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TalukaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Taluka(123);
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
        const entity = new Taluka();
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
