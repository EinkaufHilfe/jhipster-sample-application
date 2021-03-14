import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IDeduction, Deduction } from 'app/shared/model/deduction.model';
import { DeductionService } from './deduction.service';
import { IPriceList } from 'app/shared/model/price-list.model';
import { PriceListService } from 'app/entities/price-list/price-list.service';

@Component({
  selector: 'jhi-deduction-update',
  templateUrl: './deduction-update.component.html',
})
export class DeductionUpdateComponent implements OnInit {
  isSaving = false;
  pricelists: IPriceList[] = [];

  editForm = this.fb.group({
    id: [],
    reason: [],
    price: [],
    createdBy: [],
    createdAt: [],
    lastUpdatedBy: [],
    lastUpdatedAt: [],
    priceList: [],
  });

  constructor(
    protected deductionService: DeductionService,
    protected priceListService: PriceListService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deduction }) => {
      if (!deduction.id) {
        const today = moment().startOf('day');
        deduction.createdAt = today;
        deduction.lastUpdatedAt = today;
      }

      this.updateForm(deduction);

      this.priceListService.query().subscribe((res: HttpResponse<IPriceList[]>) => (this.pricelists = res.body || []));
    });
  }

  updateForm(deduction: IDeduction): void {
    this.editForm.patchValue({
      id: deduction.id,
      reason: deduction.reason,
      price: deduction.price,
      createdBy: deduction.createdBy,
      createdAt: deduction.createdAt ? deduction.createdAt.format(DATE_TIME_FORMAT) : null,
      lastUpdatedBy: deduction.lastUpdatedBy,
      lastUpdatedAt: deduction.lastUpdatedAt ? deduction.lastUpdatedAt.format(DATE_TIME_FORMAT) : null,
      priceList: deduction.priceList,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deduction = this.createFromForm();
    if (deduction.id !== undefined) {
      this.subscribeToSaveResponse(this.deductionService.update(deduction));
    } else {
      this.subscribeToSaveResponse(this.deductionService.create(deduction));
    }
  }

  private createFromForm(): IDeduction {
    return {
      ...new Deduction(),
      id: this.editForm.get(['id'])!.value,
      reason: this.editForm.get(['reason'])!.value,
      price: this.editForm.get(['price'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      lastUpdatedBy: this.editForm.get(['lastUpdatedBy'])!.value,
      lastUpdatedAt: this.editForm.get(['lastUpdatedAt'])!.value
        ? moment(this.editForm.get(['lastUpdatedAt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      priceList: this.editForm.get(['priceList'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeduction>>): void {
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
