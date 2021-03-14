import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ConditionDefinitionComponent } from 'app/entities/condition-definition/condition-definition.component';
import { ConditionDefinitionService } from 'app/entities/condition-definition/condition-definition.service';
import { ConditionDefinition } from 'app/shared/model/condition-definition.model';

describe('Component Tests', () => {
  describe('ConditionDefinition Management Component', () => {
    let comp: ConditionDefinitionComponent;
    let fixture: ComponentFixture<ConditionDefinitionComponent>;
    let service: ConditionDefinitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ConditionDefinitionComponent],
      })
        .overrideTemplate(ConditionDefinitionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConditionDefinitionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConditionDefinitionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConditionDefinition(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conditionDefinitions && comp.conditionDefinitions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
