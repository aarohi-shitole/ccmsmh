import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { MunicipalCorpUpdateComponent } from 'app/entities/municipal-corp/municipal-corp-update.component';
import { MunicipalCorpService } from 'app/entities/municipal-corp/municipal-corp.service';
import { MunicipalCorp } from 'app/shared/model/municipal-corp.model';

describe('Component Tests', () => {
  describe('MunicipalCorp Management Update Component', () => {
    let comp: MunicipalCorpUpdateComponent;
    let fixture: ComponentFixture<MunicipalCorpUpdateComponent>;
    let service: MunicipalCorpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [MunicipalCorpUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MunicipalCorpUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MunicipalCorpUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MunicipalCorpService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MunicipalCorp(123);
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
        const entity = new MunicipalCorp();
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
