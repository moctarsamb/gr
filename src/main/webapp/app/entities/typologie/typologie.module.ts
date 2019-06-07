import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    TypologieComponent,
    TypologieDetailComponent,
    TypologieUpdateComponent,
    TypologieDeletePopupComponent,
    TypologieDeleteDialogComponent,
    typologieRoute,
    typologiePopupRoute
} from './';

const ENTITY_STATES = [...typologieRoute, ...typologiePopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypologieComponent,
        TypologieDetailComponent,
        TypologieUpdateComponent,
        TypologieDeleteDialogComponent,
        TypologieDeletePopupComponent
    ],
    entryComponents: [TypologieComponent, TypologieUpdateComponent, TypologieDeleteDialogComponent, TypologieDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayTypologieModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
