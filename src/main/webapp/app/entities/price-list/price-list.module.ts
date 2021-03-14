import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PriceListComponent } from './price-list.component';
import { PriceListDetailComponent } from './price-list-detail.component';
import { PriceListUpdateComponent } from './price-list-update.component';
import { PriceListDeleteDialogComponent } from './price-list-delete-dialog.component';
import { priceListRoute } from './price-list.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(priceListRoute)],
  declarations: [PriceListComponent, PriceListDetailComponent, PriceListUpdateComponent, PriceListDeleteDialogComponent],
  entryComponents: [PriceListDeleteDialogComponent],
})
export class JhipsterSampleApplicationPriceListModule {}
