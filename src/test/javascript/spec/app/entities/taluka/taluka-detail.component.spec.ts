import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { TalukaDetailComponent } from 'app/entities/taluka/taluka-detail.component';
import { Taluka } from 'app/shared/model/taluka.model';

describe('Component Tests', () => {
  describe('Taluka Management Detail Component', () => {
    let comp: TalukaDetailComponent;
    let fixture: ComponentFixture<TalukaDetailComponent>;
    const route = ({ data: of({ taluka: new Taluka(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [TalukaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TalukaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TalukaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taluka on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taluka).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
