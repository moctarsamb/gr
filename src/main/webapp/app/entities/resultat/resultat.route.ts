import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Resultat } from 'app/shared/model/resultat.model';
import { ResultatService } from './resultat.service';
import { ResultatComponent } from './resultat.component';
import { ResultatDetailComponent } from './resultat-detail.component';
import { ResultatUpdateComponent } from './resultat-update.component';
import { ResultatDeletePopupComponent } from './resultat-delete-dialog.component';
import { IResultat } from 'app/shared/model/resultat.model';

@Injectable({ providedIn: 'root' })
export class ResultatResolve implements Resolve<IResultat> {
    constructor(private service: ResultatService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IResultat> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Resultat>) => response.ok),
                map((resultat: HttpResponse<Resultat>) => resultat.body)
            );
        }
        return of(new Resultat());
    }
}

export const resultatRoute: Routes = [
    {
        path: '',
        component: ResultatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.resultat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ResultatDetailComponent,
        resolve: {
            resultat: ResultatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.resultat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ResultatUpdateComponent,
        resolve: {
            resultat: ResultatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.resultat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ResultatUpdateComponent,
        resolve: {
            resultat: ResultatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.resultat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resultatPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ResultatDeletePopupComponent,
        resolve: {
            resultat: ResultatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.resultat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
