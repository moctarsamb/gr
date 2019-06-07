import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import {
    GestionrequisitionsfrontendgatewaySharedLibsModule,
    GestionrequisitionsfrontendgatewaySharedCommonModule,
    HasAnyAuthorityDirective
} from './';

@NgModule({
    imports: [GestionrequisitionsfrontendgatewaySharedLibsModule, GestionrequisitionsfrontendgatewaySharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    exports: [GestionrequisitionsfrontendgatewaySharedCommonModule, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewaySharedModule {
    static forRoot() {
        return {
            ngModule: GestionrequisitionsfrontendgatewaySharedModule
        };
    }
}
