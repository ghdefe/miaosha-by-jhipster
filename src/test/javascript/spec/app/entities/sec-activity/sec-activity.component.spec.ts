import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MiaoshaByJhipsterTestModule } from '../../../test.module';
import { SecActivityComponent } from 'app/entities/sec-activity/sec-activity.component';
import { SecActivityService } from 'app/entities/sec-activity/sec-activity.service';
import { SecActivity } from 'app/shared/model/sec-activity.model';

describe('Component Tests', () => {
  describe('SecActivity Management Component', () => {
    let comp: SecActivityComponent;
    let fixture: ComponentFixture<SecActivityComponent>;
    let service: SecActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MiaoshaByJhipsterTestModule],
        declarations: [SecActivityComponent],
      })
        .overrideTemplate(SecActivityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SecActivityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SecActivityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SecActivity(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.secActivities && comp.secActivities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
