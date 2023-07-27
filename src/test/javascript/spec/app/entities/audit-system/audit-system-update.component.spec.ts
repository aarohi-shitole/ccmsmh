import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { AuditSystemUpdateComponent } from 'app/entities/audit-system/audit-system-update.component';
import { AuditSystemService } from 'app/entities/audit-system/audit-system.service';
import { AuditSystem } from 'app/shared/model/audit-system.model';

describe('Component Tests', () => {
  describe('AuditSystem Management Update Component', () => {
    let comp: AuditSystemUpdateComponent;
    let fixture: ComponentFixture<AuditSystemUpdateComponent>;
    let service: AuditSystemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [AuditSystemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AuditSystemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuditSystemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditSystemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AuditSystem(123);
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
        const entity = new AuditSystem();
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
