import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PriceListDetailComponent } from 'app/entities/price-list/price-list-detail.component';
import { PriceList } from 'app/shared/model/price-list.model';

describe('Component Tests', () => {
  describe('PriceList Management Detail Component', () => {
    let comp: PriceListDetailComponent;
    let fixture: ComponentFixture<PriceListDetailComponent>;
    const route = ({ data: of({ priceList: new PriceList(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PriceListDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PriceListDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PriceListDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load priceList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.priceList).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
