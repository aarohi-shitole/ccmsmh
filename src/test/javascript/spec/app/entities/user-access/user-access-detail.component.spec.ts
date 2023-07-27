import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { UserAccessDetailComponent } from 'app/entities/user-access/user-access-detail.component';
import { UserAccess } from 'app/shared/model/user-access.model';

describe('Component Tests', () => {
  describe('UserAccess Management Detail Component', () => {
    let comp: UserAccessDetailComponent;
    let fixture: ComponentFixture<UserAccessDetailComponent>;
    const route = ({ data: of({ userAccess: new UserAccess(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [UserAccessDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserAccessDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserAccessDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userAccess on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userAccess).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
