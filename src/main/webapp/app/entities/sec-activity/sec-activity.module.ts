import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MiaoshaByJhipsterSharedModule } from 'app/shared/shared.module';
import { SecActivityComponent } from './sec-activity.component';
import { SecActivityDetailComponent } from './sec-activity-detail.component';
import { SecActivityUpdateComponent } from './sec-activity-update.component';
import { SecActivityDeleteDialogComponent } from './sec-activity-delete-dialog.component';
import { secActivityRoute } from './sec-activity.route';

@NgModule({
  imports: [MiaoshaByJhipsterSharedModule, RouterModule.forChild(secActivityRoute)],
  declarations: [SecActivityComponent, SecActivityDetailComponent, SecActivityUpdateComponent, SecActivityDeleteDialogComponent],
  entryComponents: [SecActivityDeleteDialogComponent],
})
export class MiaoshaByJhipsterSecActivityModule {}
