import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PartSaleComponent } from './part-sale.component';
import { PartSaleDetailComponent } from './part-sale-detail.component';
import { PartSaleUpdateComponent } from './part-sale-update.component';
import { PartSaleDeleteDialogComponent } from './part-sale-delete-dialog.component';
import { partSaleRoute } from './part-sale.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(partSaleRoute)],
  declarations: [PartSaleComponent, PartSaleDetailComponent, PartSaleUpdateComponent, PartSaleDeleteDialogComponent],
  entryComponents: [PartSaleDeleteDialogComponent],
})
export class JhipsterSampleApplicationPartSaleModule {}
