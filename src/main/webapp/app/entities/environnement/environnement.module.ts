import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    EnvironnementComponent,
    EnvironnementDetailComponent,
    EnvironnementUpdateComponent,
    EnvironnementDeletePopupComponent,
    EnvironnementDeleteDialogComponent,
    environnementRoute,
    environnementPopupRoute
} from './';

const ENTITY_STATES = [...environnementRoute, ...environnementPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EnvironnementComponent,
        EnvironnementDetailComponent,
        EnvironnementUpdateComponent,
        EnvironnementDeleteDialogComponent,
        EnvironnementDeletePopupComponent
    ],
    entryComponents: [
        EnvironnementComponent,
        EnvironnementUpdateComponent,
        EnvironnementDeleteDialogComponent,
        EnvironnementDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayEnvironnementModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
