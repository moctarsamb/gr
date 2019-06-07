import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Flux } from 'app/shared/model/flux.model';
import { FluxService } from './flux.service';
import { FluxComponent } from './flux.component';
import { FluxDetailComponent } from './flux-detail.component';
import { FluxUpdateComponent } from './flux-update.component';
import { FluxDeletePopupComponent } from './flux-delete-dialog.component';
import { IFlux } from 'app/shared/model/flux.model';

@Injectable({ providedIn: 'root' })
export class FluxResolve implements Resolve<IFlux> {
    constructor(private service: FluxService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFlux> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Flux>) => response.ok),
                map((flux: HttpResponse<Flux>) => flux.body)
            );
        }
        return of(new Flux());
    }
}

export const fluxRoute: Routes = [
    {
        path: '',
        component: FluxComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.flux.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FluxDetailComponent,
        resolve: {
            flux: FluxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.flux.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FluxUpdateComponent,
        resolve: {
            flux: FluxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.flux.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FluxUpdateComponent,
        resolve: {
            flux: FluxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.flux.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fluxPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FluxDeletePopupComponent,
        resolve: {
            flux: FluxResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.flux.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
