import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    ResultatComponent,
    ResultatDetailComponent,
    ResultatUpdateComponent,
    ResultatDeletePopupComponent,
    ResultatDeleteDialogComponent,
    resultatRoute,
    resultatPopupRoute
} from './';

const ENTITY_STATES = [...resultatRoute, ...resultatPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ResultatComponent,
        ResultatDetailComponent,
        ResultatUpdateComponent,
        ResultatDeleteDialogComponent,
        ResultatDeletePopupComponent
    ],
    entryComponents: [ResultatComponent, ResultatUpdateComponent, ResultatDeleteDialogComponent, ResultatDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayResultatModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
