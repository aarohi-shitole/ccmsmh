import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { SecurityPermissionDetailComponent } from 'app/entities/security-permission/security-permission-detail.component';
import { SecurityPermission } from 'app/shared/model/security-permission.model';

describe('Component Tests', () => {
  describe('SecurityPermission Management Detail Component', () => {
    let comp: SecurityPermissionDetailComponent;
    let fixture: ComponentFixture<SecurityPermissionDetailComponent>;
    const route = ({ data: of({ securityPermission: new SecurityPermission(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [SecurityPermissionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SecurityPermissionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SecurityPermissionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load securityPermission on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.securityPermission).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
