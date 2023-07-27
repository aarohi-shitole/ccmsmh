import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { UserAccessUpdateComponent } from 'app/entities/user-access/user-access-update.component';
import { UserAccessService } from 'app/entities/user-access/user-access.service';
import { UserAccess } from 'app/shared/model/user-access.model';

describe('Component Tests', () => {
  describe('UserAccess Management Update Component', () => {
    let comp: UserAccessUpdateComponent;
    let fixture: ComponentFixture<UserAccessUpdateComponent>;
    let service: UserAccessService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [UserAccessUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserAccessUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserAccessUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserAccessService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserAccess(123);
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
        const entity = new UserAccess();
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
