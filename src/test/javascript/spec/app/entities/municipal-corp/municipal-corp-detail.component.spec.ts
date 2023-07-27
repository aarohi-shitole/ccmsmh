import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { MunicipalCorpDetailComponent } from 'app/entities/municipal-corp/municipal-corp-detail.component';
import { MunicipalCorp } from 'app/shared/model/municipal-corp.model';

describe('Component Tests', () => {
  describe('MunicipalCorp Management Detail Component', () => {
    let comp: MunicipalCorpDetailComponent;
    let fixture: ComponentFixture<MunicipalCorpDetailComponent>;
    const route = ({ data: of({ municipalCorp: new MunicipalCorp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [MunicipalCorpDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MunicipalCorpDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MunicipalCorpDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load municipalCorp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.municipalCorp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
