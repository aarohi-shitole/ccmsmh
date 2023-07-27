import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { BedInventoryDetailComponent } from 'app/entities/bed-inventory/bed-inventory-detail.component';
import { BedInventory } from 'app/shared/model/bed-inventory.model';

describe('Component Tests', () => {
  describe('BedInventory Management Detail Component', () => {
    let comp: BedInventoryDetailComponent;
    let fixture: ComponentFixture<BedInventoryDetailComponent>;
    const route = ({ data: of({ bedInventory: new BedInventory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [BedInventoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BedInventoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BedInventoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bedInventory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bedInventory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
