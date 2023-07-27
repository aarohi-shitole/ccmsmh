import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CovidCareSharedModule } from 'app/shared/shared.module';
import { CovidCareCoreModule } from 'app/core/core.module';
import { CovidCareAppRoutingModule } from './app-routing.module';
import { CovidCareHomeModule } from './home/home.module';
import { CovidCareEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    CovidCareSharedModule,
    CovidCareCoreModule,
    CovidCareHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CovidCareEntityModule,
    CovidCareAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class CovidCareAppModule {}
