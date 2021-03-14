import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { ConditionDefinitionComponent } from './condition-definition.component';
import { ConditionDefinitionDetailComponent } from './condition-definition-detail.component';
import { ConditionDefinitionUpdateComponent } from './condition-definition-update.component';
import { ConditionDefinitionDeleteDialogComponent } from './condition-definition-delete-dialog.component';
import { conditionDefinitionRoute } from './condition-definition.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(conditionDefinitionRoute)],
  declarations: [
    ConditionDefinitionComponent,
    ConditionDefinitionDetailComponent,
    ConditionDefinitionUpdateComponent,
    ConditionDefinitionDeleteDialogComponent,
  ],
  entryComponents: [ConditionDefinitionDeleteDialogComponent],
})
export class JhipsterSampleApplicationConditionDefinitionModule {}
