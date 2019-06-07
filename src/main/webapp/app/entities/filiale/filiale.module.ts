import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    FilialeComponent,
    FilialeDetailComponent,
    FilialeUpdateComponent,
    FilialeDeletePopupComponent,
    FilialeDeleteDialogComponent,
    filialeRoute,
    filialePopupRoute
} from './';

const ENTITY_STATES = [...filialeRoute, ...filialePopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FilialeComponent,
        FilialeDetailComponent,
        FilialeUpdateComponent,
        FilialeDeleteDialogComponent,
        FilialeDeletePopupComponent
    ],
    entryComponents: [FilialeComponent, FilialeUpdateComponent, FilialeDeleteDialogComponent, FilialeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayFilialeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
