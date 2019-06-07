import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    CritereComponent,
    CritereDetailComponent,
    CritereUpdateComponent,
    CritereDeletePopupComponent,
    CritereDeleteDialogComponent,
    critereRoute,
    criterePopupRoute
} from './';

const ENTITY_STATES = [...critereRoute, ...criterePopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CritereComponent,
        CritereDetailComponent,
        CritereUpdateComponent,
        CritereDeleteDialogComponent,
        CritereDeletePopupComponent
    ],
    entryComponents: [CritereComponent, CritereUpdateComponent, CritereDeleteDialogComponent, CritereDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayCritereModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
