import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    UtilisateurComponent,
    UtilisateurDetailComponent,
    UtilisateurUpdateComponent,
    UtilisateurDeletePopupComponent,
    UtilisateurDeleteDialogComponent,
    utilisateurRoute,
    utilisateurPopupRoute
} from './';

const ENTITY_STATES = [...utilisateurRoute, ...utilisateurPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UtilisateurComponent,
        UtilisateurDetailComponent,
        UtilisateurUpdateComponent,
        UtilisateurDeleteDialogComponent,
        UtilisateurDeletePopupComponent
    ],
    entryComponents: [UtilisateurComponent, UtilisateurUpdateComponent, UtilisateurDeleteDialogComponent, UtilisateurDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayUtilisateurModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
