import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Base } from 'app/shared/model/base.model';
import { BaseService } from './base.service';
import { BaseComponent } from './base.component';
import { BaseDetailComponent } from './base-detail.component';
import { BaseUpdateComponent } from './base-update.component';
import { BaseDeletePopupComponent } from './base-delete-dialog.component';
import { IBase } from 'app/shared/model/base.model';

@Injectable({ providedIn: 'root' })
export class BaseResolve implements Resolve<IBase> {
    constructor(private service: BaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBase> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Base>) => response.ok),
                map((base: HttpResponse<Base>) => base.body)
            );
        }
        return of(new Base());
    }
}

export const baseRoute: Routes = [
    {
        path: '',
        component: BaseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.base.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BaseDetailComponent,
        resolve: {
            base: BaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.base.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BaseUpdateComponent,
        resolve: {
            base: BaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.base.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BaseUpdateComponent,
        resolve: {
            base: BaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.base.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const basePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BaseDeletePopupComponent,
        resolve: {
            base: BaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.base.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
