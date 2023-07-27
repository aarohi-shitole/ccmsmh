import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { SecurityRoleUpdateComponent } from 'app/entities/security-role/security-role-update.component';
import { SecurityRoleService } from 'app/entities/security-role/security-role.service';
import { SecurityRole } from 'app/shared/model/security-role.model';

describe('Component Tests', () => {
  describe('SecurityRole Management Update Component', () => {
    let comp: SecurityRoleUpdateComponent;
    let fixture: ComponentFixture<SecurityRoleUpdateComponent>;
    let service: SecurityRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [SecurityRoleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SecurityRoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SecurityRoleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SecurityRoleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SecurityRole(123);
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
        const entity = new SecurityRole();
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
