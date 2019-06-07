import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage } from 'ngx-webstorage';
import { NgJhipsterModule } from 'ng-jhipster';

import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import { GestionrequisitionsfrontendgatewayCoreModule } from 'app/core';
import { GestionrequisitionsfrontendgatewayAppRoutingModule } from './app-routing.module';
import { GestionrequisitionsfrontendgatewayHomeModule } from 'app/home';
import { GestionrequisitionsfrontendgatewayEntityModule } from './entities/entity.module';
import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';

// Nebular Theming
import { NbThemeModule } from '@nebular/theme';

@NgModule({
    imports: [
        NbThemeModule.forRoot(),
        BrowserModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        NgJhipsterModule.forRoot({
            // set below to true to make alerts look like toast
            alertAsToast: false,
            alertTimeout: 5000,
            i18nEnabled: true,
            defaultI18nLang: 'fr'
        }),
        GestionrequisitionsfrontendgatewaySharedModule.forRoot(),
        GestionrequisitionsfrontendgatewayCoreModule,
        GestionrequisitionsfrontendgatewayHomeModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
        GestionrequisitionsfrontendgatewayEntityModule,
        GestionrequisitionsfrontendgatewayAppRoutingModule
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class GestionrequisitionsfrontendgatewayAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
