import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPriceList } from 'app/shared/model/price-list.model';
import { PriceListService } from './price-list.service';

@Component({
  templateUrl: './price-list-delete-dialog.component.html',
})
export class PriceListDeleteDialogComponent {
  priceList?: IPriceList;

  constructor(protected priceListService: PriceListService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.priceListService.delete(id).subscribe(() => {
      this.eventManager.broadcast('priceListListModification');
      this.activeModal.close();
    });
  }
}
