import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ParametrageApplication } from 'app/shared/model/parametrage-application.model';
import { ParametrageApplicationService } from './parametrage-application.service';
import { ParametrageApplicationComponent } from './parametrage-application.component';
import { ParametrageApplicationDetailComponent } from './parametrage-application-detail.component';
import { ParametrageApplicationUpdateComponent } from './parametrage-application-update.component';
import { ParametrageApplicationDeletePopupComponent } from './parametrage-application-delete-dialog.component';
import { IParametrageApplication } from 'app/shared/model/parametrage-application.model';

@Injectable({ providedIn: 'root' })
export class ParametrageApplicationResolve implements Resolve<IParametrageApplication> {
    constructor(private service: ParametrageApplicationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IParametrageApplication> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ParametrageApplication>) => response.ok),
                map((parametrageApplication: HttpResponse<ParametrageApplication>) => parametrageApplication.body)
            );
        }
        return of(new ParametrageApplication());
    }
}

export const parametrageApplicationRoute: Routes = [
    {
        path: '',
        component: ParametrageApplicationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.parametrageApplication.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ParametrageApplicationDetailComponent,
        resolve: {
            parametrageApplication: ParametrageApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.parametrageApplication.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ParametrageApplicationUpdateComponent,
        resolve: {
            parametrageApplication: ParametrageApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.parametrageApplication.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ParametrageApplicationUpdateComponent,
        resolve: {
            parametrageApplication: ParametrageApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.parametrageApplication.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parametrageApplicationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ParametrageApplicationDeletePopupComponent,
        resolve: {
            parametrageApplication: ParametrageApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.parametrageApplication.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
