import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartSale } from 'app/shared/model/part-sale.model';

@Component({
  selector: 'jhi-part-sale-detail',
  templateUrl: './part-sale-detail.component.html',
})
export class PartSaleDetailComponent implements OnInit {
  partSale: IPartSale | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partSale }) => (this.partSale = partSale));
  }

  previousState(): void {
    window.history.back();
  }
}
