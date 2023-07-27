import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { SecurityPermissionUpdateComponent } from 'app/entities/security-permission/security-permission-update.component';
import { SecurityPermissionService } from 'app/entities/security-permission/security-permission.service';
import { SecurityPermission } from 'app/shared/model/security-permission.model';

describe('Component Tests', () => {
  describe('SecurityPermission Management Update Component', () => {
    let comp: SecurityPermissionUpdateComponent;
    let fixture: ComponentFixture<SecurityPermissionUpdateComponent>;
    let service: SecurityPermissionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [SecurityPermissionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SecurityPermissionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SecurityPermissionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SecurityPermissionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SecurityPermission(123);
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
        const entity = new SecurityPermission();
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
