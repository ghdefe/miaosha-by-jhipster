import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { MiaoshaByJhipsterSharedModule } from 'app/shared/shared.module';
import { MiaoshaByJhipsterCoreModule } from 'app/core/core.module';
import { MiaoshaByJhipsterAppRoutingModule } from './app-routing.module';
import { MiaoshaByJhipsterHomeModule } from './home/home.module';
import { MiaoshaByJhipsterEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    MiaoshaByJhipsterSharedModule,
    MiaoshaByJhipsterCoreModule,
    MiaoshaByJhipsterHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    MiaoshaByJhipsterEntityModule,
    MiaoshaByJhipsterAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class MiaoshaByJhipsterAppModule {}
