import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GestionrequisitionsfrontendgatewaySharedModule } from 'app/shared';
import {
    JointureComponent,
    JointureDetailComponent,
    JointureUpdateComponent,
    JointureDeletePopupComponent,
    JointureDeleteDialogComponent,
    jointureRoute,
    jointurePopupRoute
} from './';

const ENTITY_STATES = [...jointureRoute, ...jointurePopupRoute];

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        JointureComponent,
        JointureDetailComponent,
        JointureUpdateComponent,
        JointureDeleteDialogComponent,
        JointureDeletePopupComponent
    ],
    entryComponents: [JointureComponent, JointureUpdateComponent, JointureDeleteDialogComponent, JointureDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayJointureModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
