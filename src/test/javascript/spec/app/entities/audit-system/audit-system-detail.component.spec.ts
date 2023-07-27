import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { AuditSystemDetailComponent } from 'app/entities/audit-system/audit-system-detail.component';
import { AuditSystem } from 'app/shared/model/audit-system.model';

describe('Component Tests', () => {
  describe('AuditSystem Management Detail Component', () => {
    let comp: AuditSystemDetailComponent;
    let fixture: ComponentFixture<AuditSystemDetailComponent>;
    const route = ({ data: of({ auditSystem: new AuditSystem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [AuditSystemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AuditSystemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuditSystemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load auditSystem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.auditSystem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
