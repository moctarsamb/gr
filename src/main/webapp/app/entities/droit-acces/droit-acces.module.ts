import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    DroitAccesComponent,
    DroitAccesDetailComponent,
    DroitAccesUpdateComponent,
    DroitAccesDeletePopupComponent,
    DroitAccesDeleteDialogComponent,
    droitAccesRoute,
    droitAccesPopupRoute
} from './';

const ENTITY_STATES = [...droitAccesRoute, ...droitAccesPopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DroitAccesComponent,
        DroitAccesDetailComponent,
        DroitAccesUpdateComponent,
        DroitAccesDeleteDialogComponent,
        DroitAccesDeletePopupComponent
    ],
    entryComponents: [DroitAccesComponent, DroitAccesUpdateComponent, DroitAccesDeleteDialogComponent, DroitAccesDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayDroitAccesModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
