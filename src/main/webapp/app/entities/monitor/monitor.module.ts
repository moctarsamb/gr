import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    MonitorComponent,
    MonitorDetailComponent,
    MonitorUpdateComponent,
    MonitorDeletePopupComponent,
    MonitorDeleteDialogComponent,
    monitorRoute,
    monitorPopupRoute
} from './';

const ENTITY_STATES = [...monitorRoute, ...monitorPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MonitorComponent,
        MonitorDetailComponent,
        MonitorUpdateComponent,
        MonitorDeleteDialogComponent,
        MonitorDeletePopupComponent
    ],
    entryComponents: [MonitorComponent, MonitorUpdateComponent, MonitorDeleteDialogComponent, MonitorDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayMonitorModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
