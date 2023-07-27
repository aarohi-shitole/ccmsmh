import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { SecurityUserDetailComponent } from 'app/entities/security-user/security-user-detail.component';
import { SecurityUser } from 'app/shared/model/security-user.model';

describe('Component Tests', () => {
  describe('SecurityUser Management Detail Component', () => {
    let comp: SecurityUserDetailComponent;
    let fixture: ComponentFixture<SecurityUserDetailComponent>;
    const route = ({ data: of({ securityUser: new SecurityUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [SecurityUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SecurityUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SecurityUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load securityUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.securityUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
