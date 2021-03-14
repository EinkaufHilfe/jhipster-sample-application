import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartSale } from 'app/shared/model/part-sale.model';
import { PartSaleService } from './part-sale.service';

@Component({
  templateUrl: './part-sale-delete-dialog.component.html',
})
export class PartSaleDeleteDialogComponent {
  partSale?: IPartSale;

  constructor(protected partSaleService: PartSaleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partSaleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partSaleListModification');
      this.activeModal.close();
    });
  }
}
