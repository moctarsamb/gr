import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    FiltreComponent,
    FiltreDetailComponent,
    FiltreUpdateComponent,
    FiltreDeletePopupComponent,
    FiltreDeleteDialogComponent,
    filtreRoute,
    filtrePopupRoute
} from './';

const ENTITY_STATES = [...filtreRoute, ...filtrePopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FiltreComponent, FiltreDetailComponent, FiltreUpdateComponent, FiltreDeleteDialogComponent, FiltreDeletePopupComponent],
    entryComponents: [FiltreComponent, FiltreUpdateComponent, FiltreDeleteDialogComponent, FiltreDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayFiltreModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
