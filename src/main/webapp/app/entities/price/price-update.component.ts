import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPrice, Price } from 'app/shared/model/price.model';
import { PriceService } from './price.service';
import { IPriceList } from 'app/shared/model/price-list.model';
import { PriceListService } from 'app/entities/price-list/price-list.service';

@Component({
  selector: 'jhi-price-update',
  templateUrl: './price-update.component.html',
})
export class PriceUpdateComponent implements OnInit {
  isSaving = false;
  pricelists: IPriceList[] = [];

  editForm = this.fb.group({
    id: [],
    model: [],
    condition: [],
    price: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
    priceList: [],
  });

  constructor(
    protected priceService: PriceService,
    protected priceListService: PriceListService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ price }) => {
      if (!price.id) {
        const today = moment().startOf('day');
        price.createdDate = today;
        price.lastModifiedDate = today;
      }

      this.updateForm(price);

      this.priceListService.query().subscribe((res: HttpResponse<IPriceList[]>) => (this.pricelists = res.body || []));
    });
  }

  updateForm(price: IPrice): void {
    this.editForm.patchValue({
      id: price.id,
      model: price.model,
      condition: price.condition,
      price: price.price,
      createdBy: price.createdBy,
      createdDate: price.createdDate ? price.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: price.lastModifiedBy,
      lastModifiedDate: price.lastModifiedDate ? price.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      priceList: price.priceList,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const price = this.createFromForm();
    if (price.id !== undefined) {
      this.subscribeToSaveResponse(this.priceService.update(price));
    } else {
      this.subscribeToSaveResponse(this.priceService.create(price));
    }
  }

  private createFromForm(): IPrice {
    return {
      ...new Price(),
      id: this.editForm.get(['id'])!.value,
      model: this.editForm.get(['model'])!.value,
      condition: this.editForm.get(['condition'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrice>>): void {
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
