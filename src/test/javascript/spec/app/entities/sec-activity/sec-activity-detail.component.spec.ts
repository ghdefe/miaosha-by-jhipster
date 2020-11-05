import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MiaoshaByJhipsterTestModule } from '../../../test.module';
import { SecActivityDetailComponent } from 'app/entities/sec-activity/sec-activity-detail.component';
import { SecActivity } from 'app/shared/model/sec-activity.model';

describe('Component Tests', () => {
  describe('SecActivity Management Detail Component', () => {
    let comp: SecActivityDetailComponent;
    let fixture: ComponentFixture<SecActivityDetailComponent>;
    const route = ({ data: of({ secActivity: new SecActivity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MiaoshaByJhipsterTestModule],
        declarations: [SecActivityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SecActivityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SecActivityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load secActivity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.secActivity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
