import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConditionDefinition } from 'app/shared/model/condition-definition.model';
import { ConditionDefinitionService } from './condition-definition.service';
import { ConditionDefinitionDeleteDialogComponent } from './condition-definition-delete-dialog.component';

@Component({
  selector: 'jhi-condition-definition',
  templateUrl: './condition-definition.component.html',
})
export class ConditionDefinitionComponent implements OnInit, OnDestroy {
  conditionDefinitions?: IConditionDefinition[];
  eventSubscriber?: Subscription;

  constructor(
    protected conditionDefinitionService: ConditionDefinitionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conditionDefinitionService
      .query()
      .subscribe((res: HttpResponse<IConditionDefinition[]>) => (this.conditionDefinitions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConditionDefinitions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConditionDefinition): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConditionDefinitions(): void {
    this.eventSubscriber = this.eventManager.subscribe('conditionDefinitionListModification', () => this.loadAll());
  }

  delete(conditionDefinition: IConditionDefinition): void {
    const modalRef = this.modalService.open(ConditionDefinitionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conditionDefinition = conditionDefinition;
  }
}
