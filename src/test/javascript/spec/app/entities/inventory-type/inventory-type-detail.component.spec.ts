import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovidCareTestModule } from '../../../test.module';
import { InventoryTypeDetailComponent } from 'app/entities/inventory-type/inventory-type-detail.component';
import { InventoryType } from 'app/shared/model/inventory-type.model';

describe('Component Tests', () => {
  describe('InventoryType Management Detail Component', () => {
    let comp: InventoryTypeDetailComponent;
    let fixture: ComponentFixture<InventoryTypeDetailComponent>;
    const route = ({ data: of({ inventoryType: new InventoryType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovidCareTestModule],
        declarations: [InventoryTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InventoryTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load inventoryType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventoryType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
