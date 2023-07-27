import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { BedTransactionsDetailComponent } from 'app/entities/bed-transactions/bed-transactions-detail.component';
import { BedTransactions } from 'app/shared/model/bed-transactions.model';

describe('Component Tests', () => {
  describe('BedTransactions Management Detail Component', () => {
    let comp: BedTransactionsDetailComponent;
    let fixture: ComponentFixture<BedTransactionsDetailComponent>;
    const route = ({ data: of({ bedTransactions: new BedTransactions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [BedTransactionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BedTransactionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BedTransactionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bedTransactions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bedTransactions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
