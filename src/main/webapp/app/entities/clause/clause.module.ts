import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    ClauseComponent,
    ClauseDetailComponent,
    ClauseUpdateComponent,
    ClauseDeletePopupComponent,
    ClauseDeleteDialogComponent,
    clauseRoute,
    clausePopupRoute
} from './';

const ENTITY_STATES = [...clauseRoute, ...clausePopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ClauseComponent, ClauseDetailComponent, ClauseUpdateComponent, ClauseDeleteDialogComponent, ClauseDeletePopupComponent],
    entryComponents: [ClauseComponent, ClauseUpdateComponent, ClauseDeleteDialogComponent, ClauseDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayClauseModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
