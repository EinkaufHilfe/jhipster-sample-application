import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IConditionDefinition, ConditionDefinition } from 'app/shared/model/condition-definition.model';
import { ConditionDefinitionService } from './condition-definition.service';
import { IPriceList } from 'app/shared/model/price-list.model';
import { PriceListService } from 'app/entities/price-list/price-list.service';

@Component({
  selector: 'jhi-condition-definition-update',
  templateUrl: './condition-definition-update.component.html',
})
export class ConditionDefinitionUpdateComponent implements OnInit {
  isSaving = false;
  pricelists: IPriceList[] = [];

  editForm = this.fb.group({
    id: [],
    definition: [],
    description: [],
    createdBy: [],
    createdDate: [],
    lastModifiedBy: [],
    lastUpdatedAt: [],
    priceList: [],
  });

  constructor(
    protected conditionDefinitionService: ConditionDefinitionService,
    protected priceListService: PriceListService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conditionDefinition }) => {
      if (!conditionDefinition.id) {
        const today = moment().startOf('day');
        conditionDefinition.createdDate = today;
        conditionDefinition.lastUpdatedAt = today;
      }

      this.updateForm(conditionDefinition);

      this.priceListService.query().subscribe((res: HttpResponse<IPriceList[]>) => (this.pricelists = res.body || []));
    });
  }

  updateForm(conditionDefinition: IConditionDefinition): void {
    this.editForm.patchValue({
      id: conditionDefinition.id,
      definition: conditionDefinition.definition,
      description: conditionDefinition.description,
      createdBy: conditionDefinition.createdBy,
      createdDate: conditionDefinition.createdDate ? conditionDefinition.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: conditionDefinition.lastModifiedBy,
      lastUpdatedAt: conditionDefinition.lastUpdatedAt ? conditionDefinition.lastUpdatedAt.format(DATE_TIME_FORMAT) : null,
      priceList: conditionDefinition.priceList,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const conditionDefinition = this.createFromForm();
    if (conditionDefinition.id !== undefined) {
      this.subscribeToSaveResponse(this.conditionDefinitionService.update(conditionDefinition));
    } else {
      this.subscribeToSaveResponse(this.conditionDefinitionService.create(conditionDefinition));
    }
  }

  private createFromForm(): IConditionDefinition {
    return {
      ...new ConditionDefinition(),
      id: this.editForm.get(['id'])!.value,
      definition: this.editForm.get(['definition'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      lastUpdatedAt: this.editForm.get(['lastUpdatedAt'])!.value
        ? moment(this.editForm.get(['lastUpdatedAt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      priceList: this.editForm.get(['priceList'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConditionDefinition>>): void {
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
