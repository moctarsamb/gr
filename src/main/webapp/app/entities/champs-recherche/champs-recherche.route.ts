import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ChampsRecherche } from 'app/shared/model/champs-recherche.model';
import { ChampsRechercheService } from './champs-recherche.service';
import { ChampsRechercheComponent } from './champs-recherche.component';
import { ChampsRechercheDetailComponent } from './champs-recherche-detail.component';
import { ChampsRechercheUpdateComponent } from './champs-recherche-update.component';
import { ChampsRechercheDeletePopupComponent } from './champs-recherche-delete-dialog.component';
import { IChampsRecherche } from 'app/shared/model/champs-recherche.model';

@Injectable({ providedIn: 'root' })
export class ChampsRechercheResolve implements Resolve<IChampsRecherche> {
    constructor(private service: ChampsRechercheService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IChampsRecherche> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ChampsRecherche>) => response.ok),
                map((champsRecherche: HttpResponse<ChampsRecherche>) => champsRecherche.body)
            );
        }
        return of(new ChampsRecherche());
    }
}

export const champsRechercheRoute: Routes = [
    {
        path: '',
        component: ChampsRechercheComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.champsRecherche.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ChampsRechercheDetailComponent,
        resolve: {
            champsRecherche: ChampsRechercheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.champsRecherche.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ChampsRechercheUpdateComponent,
        resolve: {
            champsRecherche: ChampsRechercheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.champsRecherche.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ChampsRechercheUpdateComponent,
        resolve: {
            champsRecherche: ChampsRechercheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.champsRecherche.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const champsRecherchePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ChampsRechercheDeletePopupComponent,
        resolve: {
            champsRecherche: ChampsRechercheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.champsRecherche.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
