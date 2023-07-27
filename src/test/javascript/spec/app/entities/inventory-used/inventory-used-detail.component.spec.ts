import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { InventoryUsedDetailComponent } from 'app/entities/inventory-used/inventory-used-detail.component';
import { InventoryUsed } from 'app/shared/model/inventory-used.model';

describe('Component Tests', () => {
  describe('InventoryUsed Management Detail Component', () => {
    let comp: InventoryUsedDetailComponent;
    let fixture: ComponentFixture<InventoryUsedDetailComponent>;
    const route = ({ data: of({ inventoryUsed: new InventoryUsed(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [InventoryUsedDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InventoryUsedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryUsedDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load inventoryUsed on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventoryUsed).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
