import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Colonne } from 'app/shared/model/colonne.model';
import { ColonneService } from './colonne.service';
import { ColonneComponent } from './colonne.component';
import { ColonneDetailComponent } from './colonne-detail.component';
import { ColonneUpdateComponent } from './colonne-update.component';
import { ColonneDeletePopupComponent } from './colonne-delete-dialog.component';
import { IColonne } from 'app/shared/model/colonne.model';

@Injectable({ providedIn: 'root' })
export class ColonneResolve implements Resolve<IColonne> {
    constructor(private service: ColonneService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IColonne> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Colonne>) => response.ok),
                map((colonne: HttpResponse<Colonne>) => colonne.body)
            );
        }
        return of(new Colonne());
    }
}

export const colonneRoute: Routes = [
    {
        path: '',
        component: ColonneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.colonne.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ColonneDetailComponent,
        resolve: {
            colonne: ColonneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.colonne.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ColonneUpdateComponent,
        resolve: {
            colonne: ColonneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.colonne.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ColonneUpdateComponent,
        resolve: {
            colonne: ColonneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.colonne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const colonnePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ColonneDeletePopupComponent,
        resolve: {
            colonne: ColonneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.colonne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
