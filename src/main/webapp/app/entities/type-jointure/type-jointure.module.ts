import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    TypeJointureComponent,
    TypeJointureDetailComponent,
    TypeJointureUpdateComponent,
    TypeJointureDeletePopupComponent,
    TypeJointureDeleteDialogComponent,
    typeJointureRoute,
    typeJointurePopupRoute
} from './';

const ENTITY_STATES = [...typeJointureRoute, ...typeJointurePopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeJointureComponent,
        TypeJointureDetailComponent,
        TypeJointureUpdateComponent,
        TypeJointureDeleteDialogComponent,
        TypeJointureDeletePopupComponent
    ],
    entryComponents: [
        TypeJointureComponent,
        TypeJointureUpdateComponent,
        TypeJointureDeleteDialogComponent,
        TypeJointureDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayTypeJointureModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
