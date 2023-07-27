import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { AuditTypeUpdateComponent } from 'app/entities/audit-type/audit-type-update.component';
import { AuditTypeService } from 'app/entities/audit-type/audit-type.service';
import { AuditType } from 'app/shared/model/audit-type.model';

describe('Component Tests', () => {
  describe('AuditType Management Update Component', () => {
    let comp: AuditTypeUpdateComponent;
    let fixture: ComponentFixture<AuditTypeUpdateComponent>;
    let service: AuditTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [AuditTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AuditTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuditTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AuditType(123);
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
        const entity = new AuditType();
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
