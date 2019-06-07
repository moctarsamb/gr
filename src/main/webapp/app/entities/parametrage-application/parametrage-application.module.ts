import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    ParametrageApplicationComponent,
    ParametrageApplicationDetailComponent,
    ParametrageApplicationUpdateComponent,
    ParametrageApplicationDeletePopupComponent,
    ParametrageApplicationDeleteDialogComponent,
    parametrageApplicationRoute,
    parametrageApplicationPopupRoute
} from './';

const ENTITY_STATES = [...parametrageApplicationRoute, ...parametrageApplicationPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ParametrageApplicationComponent,
        ParametrageApplicationDetailComponent,
        ParametrageApplicationUpdateComponent,
        ParametrageApplicationDeleteDialogComponent,
        ParametrageApplicationDeletePopupComponent
    ],
    entryComponents: [
        ParametrageApplicationComponent,
        ParametrageApplicationUpdateComponent,
        ParametrageApplicationDeleteDialogComponent,
        ParametrageApplicationDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayParametrageApplicationModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
