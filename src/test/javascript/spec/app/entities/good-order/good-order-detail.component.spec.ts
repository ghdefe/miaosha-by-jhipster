import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiaoshaByJhipsterTestModule } from '../../../test.module';
import { GoodOrderDetailComponent } from 'app/entities/good-order/good-order-detail.component';
import { GoodOrder } from 'app/shared/model/good-order.model';

describe('Component Tests', () => {
  describe('GoodOrder Management Detail Component', () => {
    let comp: GoodOrderDetailComponent;
    let fixture: ComponentFixture<GoodOrderDetailComponent>;
    const route = ({ data: of({ goodOrder: new GoodOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MiaoshaByJhipsterTestModule],
        declarations: [GoodOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GoodOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoodOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load goodOrder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.goodOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
