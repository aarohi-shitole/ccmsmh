import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { TripDetailsDetailComponent } from 'app/entities/trip-details/trip-details-detail.component';
import { TripDetails } from 'app/shared/model/trip-details.model';

describe('Component Tests', () => {
  describe('TripDetails Management Detail Component', () => {
    let comp: TripDetailsDetailComponent;
    let fixture: ComponentFixture<TripDetailsDetailComponent>;
    const route = ({ data: of({ tripDetails: new TripDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [TripDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TripDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TripDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tripDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tripDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
