import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Serveur } from 'app/shared/model/serveur.model';
import { ServeurService } from './serveur.service';
import { ServeurComponent } from './serveur.component';
import { ServeurDetailComponent } from './serveur-detail.component';
import { ServeurUpdateComponent } from './serveur-update.component';
import { ServeurDeletePopupComponent } from './serveur-delete-dialog.component';
import { IServeur } from 'app/shared/model/serveur.model';

@Injectable({ providedIn: 'root' })
export class ServeurResolve implements Resolve<IServeur> {
    constructor(private service: ServeurService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServeur> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Serveur>) => response.ok),
                map((serveur: HttpResponse<Serveur>) => serveur.body)
            );
        }
        return of(new Serveur());
    }
}

export const serveurRoute: Routes = [
    {
        path: '',
        component: ServeurComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.serveur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ServeurDetailComponent,
        resolve: {
            serveur: ServeurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.serveur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ServeurUpdateComponent,
        resolve: {
            serveur: ServeurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.serveur.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ServeurUpdateComponent,
        resolve: {
            serveur: ServeurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.serveur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const serveurPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ServeurDeletePopupComponent,
        resolve: {
            serveur: ServeurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.serveur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
