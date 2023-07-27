import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { TripDetailsUpdateComponent } from 'app/entities/trip-details/trip-details-update.component';
import { TripDetailsService } from 'app/entities/trip-details/trip-details.service';
import { TripDetails } from 'app/shared/model/trip-details.model';

describe('Component Tests', () => {
  describe('TripDetails Management Update Component', () => {
    let comp: TripDetailsUpdateComponent;
    let fixture: ComponentFixture<TripDetailsUpdateComponent>;
    let service: TripDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [TripDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TripDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TripDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TripDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TripDetails(123);
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
        const entity = new TripDetails();
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
