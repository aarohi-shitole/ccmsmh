import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { InventoryUsedUpdateComponent } from 'app/entities/inventory-used/inventory-used-update.component';
import { InventoryUsedService } from 'app/entities/inventory-used/inventory-used.service';
import { InventoryUsed } from 'app/shared/model/inventory-used.model';

describe('Component Tests', () => {
  describe('InventoryUsed Management Update Component', () => {
    let comp: InventoryUsedUpdateComponent;
    let fixture: ComponentFixture<InventoryUsedUpdateComponent>;
    let service: InventoryUsedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [InventoryUsedUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InventoryUsedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryUsedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryUsedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InventoryUsed(123);
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
        const entity = new InventoryUsed();
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
