import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISecActivity } from 'app/shared/model/sec-activity.model';
import { SecActivityService } from './sec-activity.service';

@Component({
  templateUrl: './sec-activity-delete-dialog.component.html',
})
export class SecActivityDeleteDialogComponent {
  secActivity?: ISecActivity;

  constructor(
    protected secActivityService: SecActivityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.secActivityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('secActivityListModification');
      this.activeModal.close();
    });
  }
}
