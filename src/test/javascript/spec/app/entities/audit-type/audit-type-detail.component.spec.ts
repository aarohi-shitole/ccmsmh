import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { AuditTypeDetailComponent } from 'app/entities/audit-type/audit-type-detail.component';
import { AuditType } from 'app/shared/model/audit-type.model';

describe('Component Tests', () => {
  describe('AuditType Management Detail Component', () => {
    let comp: AuditTypeDetailComponent;
    let fixture: ComponentFixture<AuditTypeDetailComponent>;
    const route = ({ data: of({ auditType: new AuditType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [AuditTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AuditTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuditTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load auditType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.auditType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
