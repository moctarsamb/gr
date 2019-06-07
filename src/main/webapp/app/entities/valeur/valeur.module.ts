import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    ValeurComponent,
    ValeurDetailComponent,
    ValeurUpdateComponent,
    ValeurDeletePopupComponent,
    ValeurDeleteDialogComponent,
    valeurRoute,
    valeurPopupRoute
} from './';

const ENTITY_STATES = [...valeurRoute, ...valeurPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ValeurComponent, ValeurDetailComponent, ValeurUpdateComponent, ValeurDeleteDialogComponent, ValeurDeletePopupComponent],
    entryComponents: [ValeurComponent, ValeurUpdateComponent, ValeurDeleteDialogComponent, ValeurDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayValeurModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
