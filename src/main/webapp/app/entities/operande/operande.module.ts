import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    OperandeComponent,
    OperandeDetailComponent,
    OperandeUpdateComponent,
    OperandeDeletePopupComponent,
    OperandeDeleteDialogComponent,
    operandeRoute,
    operandePopupRoute
} from './';

const ENTITY_STATES = [...operandeRoute, ...operandePopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OperandeComponent,
        OperandeDetailComponent,
        OperandeUpdateComponent,
        OperandeDeleteDialogComponent,
        OperandeDeletePopupComponent
    ],
    entryComponents: [OperandeComponent, OperandeUpdateComponent, OperandeDeleteDialogComponent, OperandeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayOperandeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
