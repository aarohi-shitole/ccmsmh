import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { InventoryMasterUpdateComponent } from 'app/entities/inventory-master/inventory-master-update.component';
import { InventoryMasterService } from 'app/entities/inventory-master/inventory-master.service';
import { InventoryMaster } from 'app/shared/model/inventory-master.model';

describe('Component Tests', () => {
  describe('InventoryMaster Management Update Component', () => {
    let comp: InventoryMasterUpdateComponent;
    let fixture: ComponentFixture<InventoryMasterUpdateComponent>;
    let service: InventoryMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [InventoryMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InventoryMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InventoryMaster(123);
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
        const entity = new InventoryMaster();
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
