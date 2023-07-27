import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { BedInventoryUpdateComponent } from 'app/entities/bed-inventory/bed-inventory-update.component';
import { BedInventoryService } from 'app/entities/bed-inventory/bed-inventory.service';
import { BedInventory } from 'app/shared/model/bed-inventory.model';

describe('Component Tests', () => {
  describe('BedInventory Management Update Component', () => {
    let comp: BedInventoryUpdateComponent;
    let fixture: ComponentFixture<BedInventoryUpdateComponent>;
    let service: BedInventoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [BedInventoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BedInventoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BedInventoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BedInventoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BedInventory(123);
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
        const entity = new BedInventory();
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
