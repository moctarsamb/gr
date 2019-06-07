import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    TypeExtractionComponent,
    TypeExtractionDetailComponent,
    TypeExtractionUpdateComponent,
    TypeExtractionDeletePopupComponent,
    TypeExtractionDeleteDialogComponent,
    typeExtractionRoute,
    typeExtractionPopupRoute
} from './';

const ENTITY_STATES = [...typeExtractionRoute, ...typeExtractionPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeExtractionComponent,
        TypeExtractionDetailComponent,
        TypeExtractionUpdateComponent,
        TypeExtractionDeleteDialogComponent,
        TypeExtractionDeletePopupComponent
    ],
    entryComponents: [
        TypeExtractionComponent,
        TypeExtractionUpdateComponent,
        TypeExtractionDeleteDialogComponent,
        TypeExtractionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayTypeExtractionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
