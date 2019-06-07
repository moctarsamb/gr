import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Clause } from 'app/shared/model/clause.model';
import { ClauseService } from './clause.service';
import { ClauseComponent } from './clause.component';
import { ClauseDetailComponent } from './clause-detail.component';
import { ClauseUpdateComponent } from './clause-update.component';
import { ClauseDeletePopupComponent } from './clause-delete-dialog.component';
import { IClause } from 'app/shared/model/clause.model';

@Injectable({ providedIn: 'root' })
export class ClauseResolve implements Resolve<IClause> {
    constructor(private service: ClauseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClause> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Clause>) => response.ok),
                map((clause: HttpResponse<Clause>) => clause.body)
            );
        }
        return of(new Clause());
    }
}

export const clauseRoute: Routes = [
    {
        path: '',
        component: ClauseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.clause.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ClauseDetailComponent,
        resolve: {
            clause: ClauseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.clause.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ClauseUpdateComponent,
        resolve: {
            clause: ClauseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.clause.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ClauseUpdateComponent,
        resolve: {
            clause: ClauseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.clause.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clausePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ClauseDeletePopupComponent,
        resolve: {
            clause: ClauseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.clause.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
