import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { BedTypeDetailComponent } from 'app/entities/bed-type/bed-type-detail.component';
import { BedType } from 'app/shared/model/bed-type.model';

describe('Component Tests', () => {
  describe('BedType Management Detail Component', () => {
    let comp: BedTypeDetailComponent;
    let fixture: ComponentFixture<BedTypeDetailComponent>;
    const route = ({ data: of({ bedType: new BedType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [BedTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BedTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BedTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bedType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bedType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
