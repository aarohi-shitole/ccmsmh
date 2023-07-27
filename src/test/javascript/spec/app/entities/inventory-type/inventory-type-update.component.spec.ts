import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { InventoryTypeUpdateComponent } from 'app/entities/inventory-type/inventory-type-update.component';
import { InventoryTypeService } from 'app/entities/inventory-type/inventory-type.service';
import { InventoryType } from 'app/shared/model/inventory-type.model';

describe('Component Tests', () => {
  describe('InventoryType Management Update Component', () => {
    let comp: InventoryTypeUpdateComponent;
    let fixture: ComponentFixture<InventoryTypeUpdateComponent>;
    let service: InventoryTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [InventoryTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InventoryTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InventoryType(123);
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
        const entity = new InventoryType();
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
