import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DroitAcces } from 'app/shared/model/droit-acces.model';
import { DroitAccesService } from './droit-acces.service';
import { DroitAccesComponent } from './droit-acces.component';
import { DroitAccesDetailComponent } from './droit-acces-detail.component';
import { DroitAccesUpdateComponent } from './droit-acces-update.component';
import { DroitAccesDeletePopupComponent } from './droit-acces-delete-dialog.component';
import { IDroitAcces } from 'app/shared/model/droit-acces.model';

@Injectable({ providedIn: 'root' })
export class DroitAccesResolve implements Resolve<IDroitAcces> {
    constructor(private service: DroitAccesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDroitAcces> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DroitAcces>) => response.ok),
                map((droitAcces: HttpResponse<DroitAcces>) => droitAcces.body)
            );
        }
        return of(new DroitAcces());
    }
}

export const droitAccesRoute: Routes = [
    {
        path: '',
        component: DroitAccesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.droitAcces.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DroitAccesDetailComponent,
        resolve: {
            droitAcces: DroitAccesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.droitAcces.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DroitAccesUpdateComponent,
        resolve: {
            droitAcces: DroitAccesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.droitAcces.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DroitAccesUpdateComponent,
        resolve: {
            droitAcces: DroitAccesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.droitAcces.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const droitAccesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DroitAccesDeletePopupComponent,
        resolve: {
            droitAcces: DroitAccesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.droitAcces.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
