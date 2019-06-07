import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    EnvoiResultatComponent,
    EnvoiResultatDetailComponent,
    EnvoiResultatUpdateComponent,
    EnvoiResultatDeletePopupComponent,
    EnvoiResultatDeleteDialogComponent,
    envoiResultatRoute,
    envoiResultatPopupRoute
} from './';

const ENTITY_STATES = [...envoiResultatRoute, ...envoiResultatPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EnvoiResultatComponent,
        EnvoiResultatDetailComponent,
        EnvoiResultatUpdateComponent,
        EnvoiResultatDeleteDialogComponent,
        EnvoiResultatDeletePopupComponent
    ],
    entryComponents: [
        EnvoiResultatComponent,
        EnvoiResultatUpdateComponent,
        EnvoiResultatDeleteDialogComponent,
        EnvoiResultatDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayEnvoiResultatModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
