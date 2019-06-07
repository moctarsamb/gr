import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    BaseComponent,
    BaseDetailComponent,
    BaseUpdateComponent,
    BaseDeletePopupComponent,
    BaseDeleteDialogComponent,
    baseRoute,
    basePopupRoute
} from './';

const ENTITY_STATES = [...baseRoute, ...basePopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BaseComponent, BaseDetailComponent, BaseUpdateComponent, BaseDeleteDialogComponent, BaseDeletePopupComponent],
    entryComponents: [BaseComponent, BaseUpdateComponent, BaseDeleteDialogComponent, BaseDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayBaseModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
