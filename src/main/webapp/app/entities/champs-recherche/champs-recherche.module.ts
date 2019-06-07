import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    ChampsRechercheComponent,
    ChampsRechercheDetailComponent,
    ChampsRechercheUpdateComponent,
    ChampsRechercheDeletePopupComponent,
    ChampsRechercheDeleteDialogComponent,
    champsRechercheRoute,
    champsRecherchePopupRoute
} from './';

const ENTITY_STATES = [...champsRechercheRoute, ...champsRecherchePopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChampsRechercheComponent,
        ChampsRechercheDetailComponent,
        ChampsRechercheUpdateComponent,
        ChampsRechercheDeleteDialogComponent,
        ChampsRechercheDeletePopupComponent
    ],
    entryComponents: [
        ChampsRechercheComponent,
        ChampsRechercheUpdateComponent,
        ChampsRechercheDeleteDialogComponent,
        ChampsRechercheDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayChampsRechercheModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
