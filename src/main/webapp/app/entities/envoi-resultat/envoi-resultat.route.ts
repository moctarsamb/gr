import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EnvoiResultat } from 'app/shared/model/envoi-resultat.model';
import { EnvoiResultatService } from './envoi-resultat.service';
import { EnvoiResultatComponent } from './envoi-resultat.component';
import { EnvoiResultatDetailComponent } from './envoi-resultat-detail.component';
import { EnvoiResultatUpdateComponent } from './envoi-resultat-update.component';
import { EnvoiResultatDeletePopupComponent } from './envoi-resultat-delete-dialog.component';
import { IEnvoiResultat } from 'app/shared/model/envoi-resultat.model';

@Injectable({ providedIn: 'root' })
export class EnvoiResultatResolve implements Resolve<IEnvoiResultat> {
    constructor(private service: EnvoiResultatService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEnvoiResultat> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EnvoiResultat>) => response.ok),
                map((envoiResultat: HttpResponse<EnvoiResultat>) => envoiResultat.body)
            );
        }
        return of(new EnvoiResultat());
    }
}

export const envoiResultatRoute: Routes = [
    {
        path: '',
        component: EnvoiResultatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.envoiResultat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EnvoiResultatDetailComponent,
        resolve: {
            envoiResultat: EnvoiResultatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.envoiResultat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EnvoiResultatUpdateComponent,
        resolve: {
            envoiResultat: EnvoiResultatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.envoiResultat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EnvoiResultatUpdateComponent,
        resolve: {
            envoiResultat: EnvoiResultatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.envoiResultat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const envoiResultatPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EnvoiResultatDeletePopupComponent,
        resolve: {
            envoiResultat: EnvoiResultatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.envoiResultat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
