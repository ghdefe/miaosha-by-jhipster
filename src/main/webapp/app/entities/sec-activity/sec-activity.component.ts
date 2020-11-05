import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISecActivity } from 'app/shared/model/sec-activity.model';
import { SecActivityService } from './sec-activity.service';
import { SecActivityDeleteDialogComponent } from './sec-activity-delete-dialog.component';

@Component({
  selector: 'jhi-sec-activity',
  templateUrl: './sec-activity.component.html',
})
export class SecActivityComponent implements OnInit, OnDestroy {
  secActivities?: ISecActivity[];
  eventSubscriber?: Subscription;

  constructor(
    protected secActivityService: SecActivityService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.secActivityService.query().subscribe((res: HttpResponse<ISecActivity[]>) => (this.secActivities = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSecActivities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISecActivity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSecActivities(): void {
    this.eventSubscriber = this.eventManager.subscribe('secActivityListModification', () => this.loadAll());
  }

  delete(secActivity: ISecActivity): void {
    const modalRef = this.modalService.open(SecActivityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.secActivity = secActivity;
  }
}
