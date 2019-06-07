import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FichierResultat } from 'app/shared/model/fichier-resultat.model';
import { FichierResultatService } from './fichier-resultat.service';
import { FichierResultatComponent } from './fichier-resultat.component';
import { FichierResultatDetailComponent } from './fichier-resultat-detail.component';
import { FichierResultatUpdateComponent } from './fichier-resultat-update.component';
import { FichierResultatDeletePopupComponent } from './fichier-resultat-delete-dialog.component';
import { IFichierResultat } from 'app/shared/model/fichier-resultat.model';

@Injectable({ providedIn: 'root' })
export class FichierResultatResolve implements Resolve<IFichierResultat> {
    constructor(private service: FichierResultatService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFichierResultat> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FichierResultat>) => response.ok),
                map((fichierResultat: HttpResponse<FichierResultat>) => fichierResultat.body)
            );
        }
        return of(new FichierResultat());
    }
}

export const fichierResultatRoute: Routes = [
    {
        path: '',
        component: FichierResultatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.fichierResultat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FichierResultatDetailComponent,
        resolve: {
            fichierResultat: FichierResultatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.fichierResultat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FichierResultatUpdateComponent,
        resolve: {
            fichierResultat: FichierResultatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.fichierResultat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FichierResultatUpdateComponent,
        resolve: {
            fichierResultat: FichierResultatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.fichierResultat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fichierResultatPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FichierResultatDeletePopupComponent,
        resolve: {
            fichierResultat: FichierResultatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.fichierResultat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
