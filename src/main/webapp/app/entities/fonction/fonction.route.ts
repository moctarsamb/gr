import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Fonction } from 'app/shared/model/fonction.model';
import { FonctionService } from './fonction.service';
import { FonctionComponent } from './fonction.component';
import { FonctionDetailComponent } from './fonction-detail.component';
import { FonctionUpdateComponent } from './fonction-update.component';
import { FonctionDeletePopupComponent } from './fonction-delete-dialog.component';
import { IFonction } from 'app/shared/model/fonction.model';

@Injectable({ providedIn: 'root' })
export class FonctionResolve implements Resolve<IFonction> {
    constructor(private service: FonctionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFonction> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Fonction>) => response.ok),
                map((fonction: HttpResponse<Fonction>) => fonction.body)
            );
        }
        return of(new Fonction());
    }
}

export const fonctionRoute: Routes = [
    {
        path: '',
        component: FonctionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FonctionDetailComponent,
        resolve: {
            fonction: FonctionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FonctionUpdateComponent,
        resolve: {
            fonction: FonctionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FonctionUpdateComponent,
        resolve: {
            fonction: FonctionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fonctionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FonctionDeletePopupComponent,
        resolve: {
            fonction: FonctionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
