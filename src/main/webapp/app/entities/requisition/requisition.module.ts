import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    RequisitionComponent,
    RequisitionDetailComponent,
    RequisitionUpdateComponent,
    RequisitionDeletePopupComponent,
    RequisitionDeleteDialogComponent,
    requisitionRoute,
    requisitionPopupRoute
} from './';

const ENTITY_STATES = [...requisitionRoute, ...requisitionPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RequisitionComponent,
        RequisitionDetailComponent,
        RequisitionUpdateComponent,
        RequisitionDeleteDialogComponent,
        RequisitionDeletePopupComponent
    ],
    entryComponents: [RequisitionComponent, RequisitionUpdateComponent, RequisitionDeleteDialogComponent, RequisitionDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayRequisitionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
