import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MiaoshaByJhipsterTestModule } from '../../../test.module';
import { GoodOrderComponent } from 'app/entities/good-order/good-order.component';
import { GoodOrderService } from 'app/entities/good-order/good-order.service';
import { GoodOrder } from 'app/shared/model/good-order.model';

describe('Component Tests', () => {
  describe('GoodOrder Management Component', () => {
    let comp: GoodOrderComponent;
    let fixture: ComponentFixture<GoodOrderComponent>;
    let service: GoodOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MiaoshaByJhipsterTestModule],
        declarations: [GoodOrderComponent],
      })
        .overrideTemplate(GoodOrderComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoodOrderComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoodOrderService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GoodOrder(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.goodOrders && comp.goodOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
