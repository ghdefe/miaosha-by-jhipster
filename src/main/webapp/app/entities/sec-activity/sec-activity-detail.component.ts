import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISecActivity } from 'app/shared/model/sec-activity.model';

@Component({
  selector: 'jhi-sec-activity-detail',
  templateUrl: './sec-activity-detail.component.html',
})
export class SecActivityDetailComponent implements OnInit {
  secActivity: ISecActivity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ secActivity }) => (this.secActivity = secActivity));
  }

  previousState(): void {
    window.history.back();
  }
}
