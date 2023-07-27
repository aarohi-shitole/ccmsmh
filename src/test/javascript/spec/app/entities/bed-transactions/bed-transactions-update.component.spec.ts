import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { BedTransactionsUpdateComponent } from 'app/entities/bed-transactions/bed-transactions-update.component';
import { BedTransactionsService } from 'app/entities/bed-transactions/bed-transactions.service';
import { BedTransactions } from 'app/shared/model/bed-transactions.model';

describe('Component Tests', () => {
  describe('BedTransactions Management Update Component', () => {
    let comp: BedTransactionsUpdateComponent;
    let fixture: ComponentFixture<BedTransactionsUpdateComponent>;
    let service: BedTransactionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [BedTransactionsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BedTransactionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BedTransactionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BedTransactionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BedTransactions(123);
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
        const entity = new BedTransactions();
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
