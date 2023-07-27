import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { SecurityRoleDetailComponent } from 'app/entities/security-role/security-role-detail.component';
import { SecurityRole } from 'app/shared/model/security-role.model';

describe('Component Tests', () => {
  describe('SecurityRole Management Detail Component', () => {
    let comp: SecurityRoleDetailComponent;
    let fixture: ComponentFixture<SecurityRoleDetailComponent>;
    const route = ({ data: of({ securityRole: new SecurityRole(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [SecurityRoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SecurityRoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SecurityRoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load securityRole on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.securityRole).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
