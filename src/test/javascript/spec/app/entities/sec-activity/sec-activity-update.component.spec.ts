import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MiaoshaByJhipsterTestModule } from '../../../test.module';
import { SecActivityUpdateComponent } from 'app/entities/sec-activity/sec-activity-update.component';
import { SecActivityService } from 'app/entities/sec-activity/sec-activity.service';
import { SecActivity } from 'app/shared/model/sec-activity.model';

describe('Component Tests', () => {
  describe('SecActivity Management Update Component', () => {
    let comp: SecActivityUpdateComponent;
    let fixture: ComponentFixture<SecActivityUpdateComponent>;
    let service: SecActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MiaoshaByJhipsterTestModule],
        declarations: [SecActivityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SecActivityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SecActivityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SecActivityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SecActivity(123);
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
        const entity = new SecActivity();
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
