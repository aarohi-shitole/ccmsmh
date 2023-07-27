import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { StateDetailComponent } from 'app/entities/state/state-detail.component';
import { State } from 'app/shared/model/state.model';

describe('Component Tests', () => {
  describe('State Management Detail Component', () => {
    let comp: StateDetailComponent;
    let fixture: ComponentFixture<StateDetailComponent>;
    const route = ({ data: of({ state: new State(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [StateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(StateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load state on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.state).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
