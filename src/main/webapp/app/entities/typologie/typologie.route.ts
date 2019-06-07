import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Typologie } from 'app/shared/model/typologie.model';
import { TypologieService } from './typologie.service';
import { TypologieComponent } from './typologie.component';
import { TypologieDetailComponent } from './typologie-detail.component';
import { TypologieUpdateComponent } from './typologie-update.component';
import { TypologieDeletePopupComponent } from './typologie-delete-dialog.component';
import { ITypologie } from 'app/shared/model/typologie.model';

@Injectable({ providedIn: 'root' })
export class TypologieResolve implements Resolve<ITypologie> {
    constructor(private service: TypologieService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypologie> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Typologie>) => response.ok),
                map((typologie: HttpResponse<Typologie>) => typologie.body)
            );
        }
        return of(new Typologie());
    }
}

export const typologieRoute: Routes = [
    {
        path: '',
        component: TypologieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typologie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypologieDetailComponent,
        resolve: {
            typologie: TypologieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typologie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypologieUpdateComponent,
        resolve: {
            typologie: TypologieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typologie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypologieUpdateComponent,
        resolve: {
            typologie: TypologieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typologie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typologiePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypologieDeletePopupComponent,
        resolve: {
            typologie: TypologieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typologie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
