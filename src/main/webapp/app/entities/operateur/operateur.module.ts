import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    OperateurComponent,
    OperateurDetailComponent,
    OperateurUpdateComponent,
    OperateurDeletePopupComponent,
    OperateurDeleteDialogComponent,
    operateurRoute,
    operateurPopupRoute
} from './';

const ENTITY_STATES = [...operateurRoute, ...operateurPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OperateurComponent,
        OperateurDetailComponent,
        OperateurUpdateComponent,
        OperateurDeleteDialogComponent,
        OperateurDeletePopupComponent
    ],
    entryComponents: [OperateurComponent, OperateurUpdateComponent, OperateurDeleteDialogComponent, OperateurDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayOperateurModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
