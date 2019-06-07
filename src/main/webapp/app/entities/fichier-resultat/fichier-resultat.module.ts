import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    FichierResultatComponent,
    FichierResultatDetailComponent,
    FichierResultatUpdateComponent,
    FichierResultatDeletePopupComponent,
    FichierResultatDeleteDialogComponent,
    fichierResultatRoute,
    fichierResultatPopupRoute
} from './';

const ENTITY_STATES = [...fichierResultatRoute, ...fichierResultatPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FichierResultatComponent,
        FichierResultatDetailComponent,
        FichierResultatUpdateComponent,
        FichierResultatDeleteDialogComponent,
        FichierResultatDeletePopupComponent
    ],
    entryComponents: [
        FichierResultatComponent,
        FichierResultatUpdateComponent,
        FichierResultatDeleteDialogComponent,
        FichierResultatDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayFichierResultatModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
