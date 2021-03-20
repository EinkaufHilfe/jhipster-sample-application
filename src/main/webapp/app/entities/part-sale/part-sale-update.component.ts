import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPartSale, PartSale } from 'app/shared/model/part-sale.model';
import { PartSaleService } from './part-sale.service';
import { IPriceList } from 'app/shared/model/price-list.model';
import { PriceListService } from 'app/entities/price-list/price-list.service';

@Component({
  selector: 'jhi-part-sale-update',
  templateUrl: './part-sale-update.component.html',
})
export class PartSaleUpdateComponent implements OnInit {
  isSaving = false;
  pricelists: IPriceList[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    price: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
    priceList: [],
  });

  constructor(
    protected partSaleService: PartSaleService,
    protected priceListService: PriceListService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partSale }) => {
      if (!partSale.id) {
        const today = moment().startOf('day');
        partSale.createdDate = today;
        partSale.lastModifiedDate = today;
      }

      this.updateForm(partSale);

      this.priceListService.query().subscribe((res: HttpResponse<IPriceList[]>) => (this.pricelists = res.body || []));
    });
  }

  updateForm(partSale: IPartSale): void {
    this.editForm.patchValue({
      id: partSale.id,
      name: partSale.name,
      price: partSale.price,
      createdBy: partSale.createdBy,
      createdDate: partSale.createdDate ? partSale.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: partSale.lastModifiedBy,
      lastModifiedDate: partSale.lastModifiedDate ? partSale.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      priceList: partSale.priceList,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partSale = this.createFromForm();
    if (partSale.id !== undefined) {
      this.subscribeToSaveResponse(this.partSaleService.update(partSale));
    } else {
      this.subscribeToSaveResponse(this.partSaleService.create(partSale));
    }
  }

  private createFromForm(): IPartSale {
    return {
      ...new PartSale(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      price: this.editForm.get(['price'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      priceList: this.editForm.get(['priceList'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartSale>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IPriceList): any {
    return item.id;
  }
}
