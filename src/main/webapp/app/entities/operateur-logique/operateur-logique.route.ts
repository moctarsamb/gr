import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OperateurLogique } from 'app/shared/model/operateur-logique.model';
import { OperateurLogiqueService } from './operateur-logique.service';
import { OperateurLogiqueComponent } from './operateur-logique.component';
import { OperateurLogiqueDetailComponent } from './operateur-logique-detail.component';
import { OperateurLogiqueUpdateComponent } from './operateur-logique-update.component';
import { OperateurLogiqueDeletePopupComponent } from './operateur-logique-delete-dialog.component';
import { IOperateurLogique } from 'app/shared/model/operateur-logique.model';

@Injectable({ providedIn: 'root' })
export class OperateurLogiqueResolve implements Resolve<IOperateurLogique> {
    constructor(private service: OperateurLogiqueService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOperateurLogique> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<OperateurLogique>) => response.ok),
                map((operateurLogique: HttpResponse<OperateurLogique>) => operateurLogique.body)
            );
        }
        return of(new OperateurLogique());
    }
}

export const operateurLogiqueRoute: Routes = [
    {
        path: '',
        component: OperateurLogiqueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.operateurLogique.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: OperateurLogiqueDetailComponent,
        resolve: {
            operateurLogique: OperateurLogiqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.operateurLogique.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: OperateurLogiqueUpdateComponent,
        resolve: {
            operateurLogique: OperateurLogiqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.operateurLogique.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: OperateurLogiqueUpdateComponent,
        resolve: {
            operateurLogique: OperateurLogiqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.operateurLogique.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const operateurLogiquePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: OperateurLogiqueDeletePopupComponent,
        resolve: {
            operateurLogique: OperateurLogiqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.operateurLogique.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
