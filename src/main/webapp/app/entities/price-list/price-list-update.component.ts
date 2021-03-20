import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPriceList, PriceList } from 'app/shared/model/price-list.model';
import { PriceListService } from './price-list.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-price-list-update',
  templateUrl: './price-list-update.component.html',
})
export class PriceListUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];
  validFromDp: any;
  validTillDp: any;

  editForm = this.fb.group({
    id: [],
    validFrom: [],
    validTill: [],
    type: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastModifiedDate: [],
    customer: [],
  });

  constructor(
    protected priceListService: PriceListService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ priceList }) => {
      if (!priceList.id) {
        const today = moment().startOf('day');
        priceList.createdDate = today;
        priceList.lastModifiedDate = today;
      }

      this.updateForm(priceList);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(priceList: IPriceList): void {
    this.editForm.patchValue({
      id: priceList.id,
      validFrom: priceList.validFrom,
      validTill: priceList.validTill,
      type: priceList.type,
      createdBy: priceList.createdBy,
      createdDate: priceList.createdDate ? priceList.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: priceList.lastModifiedBy,
      lastModifiedDate: priceList.lastModifiedDate ? priceList.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      customer: priceList.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const priceList = this.createFromForm();
    if (priceList.id !== undefined) {
      this.subscribeToSaveResponse(this.priceListService.update(priceList));
    } else {
      this.subscribeToSaveResponse(this.priceListService.create(priceList));
    }
  }

  private createFromForm(): IPriceList {
    return {
      ...new PriceList(),
      id: this.editForm.get(['id'])!.value,
      validFrom: this.editForm.get(['validFrom'])!.value,
      validTill: this.editForm.get(['validTill'])!.value,
      type: this.editForm.get(['type'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPriceList>>): void {
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

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
