import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { InventoryMasterDetailComponent } from 'app/entities/inventory-master/inventory-master-detail.component';
import { InventoryMaster } from 'app/shared/model/inventory-master.model';

describe('Component Tests', () => {
  describe('InventoryMaster Management Detail Component', () => {
    let comp: InventoryMasterDetailComponent;
    let fixture: ComponentFixture<InventoryMasterDetailComponent>;
    const route = ({ data: of({ inventoryMaster: new InventoryMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [InventoryMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InventoryMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load inventoryMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventoryMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
