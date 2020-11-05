import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IGood, Good } from 'app/shared/model/good.model';
import { GoodService } from './good.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-good-update',
  templateUrl: './good-update.component.html',
})
export class GoodUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(20)]],
    imgUrl: [],
    detail: [],
    price: [null, [Validators.required, Validators.min(0)]],
    sellerId: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected goodService: GoodService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ good }) => {
      this.updateForm(good);
    });
  }

  updateForm(good: IGood): void {
    this.editForm.patchValue({
      id: good.id,
      name: good.name,
      imgUrl: good.imgUrl,
      detail: good.detail,
      price: good.price,
      sellerId: good.sellerId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('miaoshaByJhipsterApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const good = this.createFromForm();
    if (good.id !== undefined) {
      this.subscribeToSaveResponse(this.goodService.update(good));
    } else {
      this.subscribeToSaveResponse(this.goodService.create(good));
    }
  }

  private createFromForm(): IGood {
    return {
      ...new Good(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      imgUrl: this.editForm.get(['imgUrl'])!.value,
      detail: this.editForm.get(['detail'])!.value,
      price: this.editForm.get(['price'])!.value,
      sellerId: this.editForm.get(['sellerId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGood>>): void {
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
}
