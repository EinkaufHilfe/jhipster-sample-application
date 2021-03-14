import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartSale } from 'app/shared/model/part-sale.model';
import { PartSaleService } from './part-sale.service';
import { PartSaleDeleteDialogComponent } from './part-sale-delete-dialog.component';

@Component({
  selector: 'jhi-part-sale',
  templateUrl: './part-sale.component.html',
})
export class PartSaleComponent implements OnInit, OnDestroy {
  partSales?: IPartSale[];
  eventSubscriber?: Subscription;

  constructor(protected partSaleService: PartSaleService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.partSaleService.query().subscribe((res: HttpResponse<IPartSale[]>) => (this.partSales = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartSales();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartSale): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartSales(): void {
    this.eventSubscriber = this.eventManager.subscribe('partSaleListModification', () => this.loadAll());
  }

  delete(partSale: IPartSale): void {
    const modalRef = this.modalService.open(PartSaleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partSale = partSale;
  }
}
