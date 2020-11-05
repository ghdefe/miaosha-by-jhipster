import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MiaoshaByJhipsterTestModule } from '../../../test.module';
import { GoodOrderUpdateComponent } from 'app/entities/good-order/good-order-update.component';
import { GoodOrderService } from 'app/entities/good-order/good-order.service';
import { GoodOrder } from 'app/shared/model/good-order.model';

describe('Component Tests', () => {
  describe('GoodOrder Management Update Component', () => {
    let comp: GoodOrderUpdateComponent;
    let fixture: ComponentFixture<GoodOrderUpdateComponent>;
    let service: GoodOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MiaoshaByJhipsterTestModule],
        declarations: [GoodOrderUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GoodOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoodOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoodOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GoodOrder(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new GoodOrder();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
