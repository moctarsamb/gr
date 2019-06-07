import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    ColonneComponent,
    ColonneDetailComponent,
    ColonneUpdateComponent,
    ColonneDeletePopupComponent,
    ColonneDeleteDialogComponent,
    colonneRoute,
    colonnePopupRoute
} from './';

const ENTITY_STATES = [...colonneRoute, ...colonnePopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ColonneComponent,
        ColonneDetailComponent,
        ColonneUpdateComponent,
        ColonneDeleteDialogComponent,
        ColonneDeletePopupComponent
    ],
    entryComponents: [ColonneComponent, ColonneUpdateComponent, ColonneDeleteDialogComponent, ColonneDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayColonneModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
