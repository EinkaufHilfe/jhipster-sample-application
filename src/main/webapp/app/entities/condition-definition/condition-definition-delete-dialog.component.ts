import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConditionDefinition } from 'app/shared/model/condition-definition.model';
import { ConditionDefinitionService } from './condition-definition.service';

@Component({
  templateUrl: './condition-definition-delete-dialog.component.html',
})
export class ConditionDefinitionDeleteDialogComponent {
  conditionDefinition?: IConditionDefinition;

  constructor(
    protected conditionDefinitionService: ConditionDefinitionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.conditionDefinitionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('conditionDefinitionListModification');
      this.activeModal.close();
    });
  }
}
