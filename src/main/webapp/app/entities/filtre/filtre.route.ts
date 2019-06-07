import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Filtre } from 'app/shared/model/filtre.model';
import { FiltreService } from './filtre.service';
import { FiltreComponent } from './filtre.component';
import { FiltreDetailComponent } from './filtre-detail.component';
import { FiltreUpdateComponent } from './filtre-update.component';
import { FiltreDeletePopupComponent } from './filtre-delete-dialog.component';
import { IFiltre } from 'app/shared/model/filtre.model';

@Injectable({ providedIn: 'root' })
export class FiltreResolve implements Resolve<IFiltre> {
    constructor(private service: FiltreService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFiltre> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Filtre>) => response.ok),
                map((filtre: HttpResponse<Filtre>) => filtre.body)
            );
        }
        return of(new Filtre());
    }
}

export const filtreRoute: Routes = [
    {
        path: '',
        component: FiltreComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.filtre.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FiltreDetailComponent,
        resolve: {
            filtre: FiltreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.filtre.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FiltreUpdateComponent,
        resolve: {
            filtre: FiltreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.filtre.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FiltreUpdateComponent,
        resolve: {
            filtre: FiltreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.filtre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const filtrePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FiltreDeletePopupComponent,
        resolve: {
            filtre: FiltreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.filtre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
