import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Valeur } from 'app/shared/model/valeur.model';
import { ValeurService } from './valeur.service';
import { ValeurComponent } from './valeur.component';
import { ValeurDetailComponent } from './valeur-detail.component';
import { ValeurUpdateComponent } from './valeur-update.component';
import { ValeurDeletePopupComponent } from './valeur-delete-dialog.component';
import { IValeur } from 'app/shared/model/valeur.model';

@Injectable({ providedIn: 'root' })
export class ValeurResolve implements Resolve<IValeur> {
    constructor(private service: ValeurService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IValeur> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Valeur>) => response.ok),
                map((valeur: HttpResponse<Valeur>) => valeur.body)
            );
        }
        return of(new Valeur());
    }
}

export const valeurRoute: Routes = [
    {
        path: '',
        component: ValeurComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.valeur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ValeurDetailComponent,
        resolve: {
            valeur: ValeurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.valeur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ValeurUpdateComponent,
        resolve: {
            valeur: ValeurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.valeur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ValeurUpdateComponent,
        resolve: {
            valeur: ValeurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.valeur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const valeurPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ValeurDeletePopupComponent,
        resolve: {
            valeur: ValeurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.valeur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
