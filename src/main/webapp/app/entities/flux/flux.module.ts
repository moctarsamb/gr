import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    FluxComponent,
    FluxDetailComponent,
    FluxUpdateComponent,
    FluxDeletePopupComponent,
    FluxDeleteDialogComponent,
    fluxRoute,
    fluxPopupRoute
} from './';

const ENTITY_STATES = [...fluxRoute, ...fluxPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FluxComponent, FluxDetailComponent, FluxUpdateComponent, FluxDeleteDialogComponent, FluxDeletePopupComponent],
    entryComponents: [FluxComponent, FluxUpdateComponent, FluxDeleteDialogComponent, FluxDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayFluxModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
