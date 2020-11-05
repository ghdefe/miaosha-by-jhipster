import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISecActivity, SecActivity } from 'app/shared/model/sec-activity.model';
import { SecActivityService } from './sec-activity.service';

@Component({
  selector: 'jhi-sec-activity-update',
  templateUrl: './sec-activity-update.component.html',
})
export class SecActivityUpdateComponent implements OnInit {
  isSaving = false;
  startDp: any;
  endDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    goodId: [null, [Validators.required]],
    author: [],
    start: [null, [Validators.required]],
    end: [null, [Validators.required]],
  });

  constructor(protected secActivityService: SecActivityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ secActivity }) => {
      this.updateForm(secActivity);
    });
  }

  updateForm(secActivity: ISecActivity): void {
    this.editForm.patchValue({
      id: secActivity.id,
      name: secActivity.name,
      goodId: secActivity.goodId,
      author: secActivity.author,
      start: secActivity.start,
      end: secActivity.end,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const secActivity = this.createFromForm();
    if (secActivity.id !== undefined) {
      this.subscribeToSaveResponse(this.secActivityService.update(secActivity));
    } else {
      this.subscribeToSaveResponse(this.secActivityService.create(secActivity));
    }
  }

  private createFromForm(): ISecActivity {
    return {
      ...new SecActivity(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      goodId: this.editForm.get(['goodId'])!.value,
      author: this.editForm.get(['author'])!.value,
      start: this.editForm.get(['start'])!.value,
      end: this.editForm.get(['end'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecActivity>>): void {
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
