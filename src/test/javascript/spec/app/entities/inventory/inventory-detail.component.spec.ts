import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { InventoryDetailComponent } from 'app/entities/inventory/inventory-detail.component';
import { Inventory } from 'app/shared/model/inventory.model';

describe('Component Tests', () => {
  describe('Inventory Management Detail Component', () => {
    let comp: InventoryDetailComponent;
    let fixture: ComponentFixture<InventoryDetailComponent>;
    const route = ({ data: of({ inventory: new Inventory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [InventoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InventoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load inventory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
