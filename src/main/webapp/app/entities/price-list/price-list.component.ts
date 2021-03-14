import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPriceList } from 'app/shared/model/price-list.model';
import { PriceListService } from './price-list.service';
import { PriceListDeleteDialogComponent } from './price-list-delete-dialog.component';

@Component({
  selector: 'jhi-price-list',
  templateUrl: './price-list.component.html',
})
export class PriceListComponent implements OnInit, OnDestroy {
  priceLists?: IPriceList[];
  eventSubscriber?: Subscription;

  constructor(protected priceListService: PriceListService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.priceListService.query().subscribe((res: HttpResponse<IPriceList[]>) => (this.priceLists = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPriceLists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPriceList): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPriceLists(): void {
    this.eventSubscriber = this.eventManager.subscribe('priceListListModification', () => this.loadAll());
  }

  delete(priceList: IPriceList): void {
    const modalRef = this.modalService.open(PriceListDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.priceList = priceList;
  }
}
